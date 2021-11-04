/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package jdk.internal.foreign;

import jdk.incubator.foreign.MappedMemorySegment;
import jdk.internal.access.JavaNioAccess;
import jdk.internal.access.SharedSecrets;
import jdk.internal.access.foreign.UnmapperProxy;
import sun.nio.ch.FileChannelImpl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Implementation for a mapped memory segments. A mapped memory segment is a native memory segment, which
 * additionally features an {@link UnmapperProxy} object. This object provides detailed information about the
 * memory mapped segment, such as the file descriptor associated with the mapping. This information is crucial
 * in order to correctly reconstruct a byte buffer object from the segment (see {@link #makeByteBuffer()}).
 */
public class MappedMemorySegmentImpl extends NativeMemorySegmentImpl implements MappedMemorySegment {

    private final UnmapperProxy unmapper;

    MappedMemorySegmentImpl(long min, UnmapperProxy unmapper, long length, int mask, MemoryScope scope) {
        super(min, length, mask, scope);
        this.unmapper = unmapper;
    }

    @Override
    ByteBuffer makeByteBuffer() {
        JavaNioAccess nioAccess = SharedSecrets.getJavaNioAccess();
        return nioAccess.newMappedByteBuffer(unmapper, min, (int)length, null, this);
    }

    @Override
    MappedMemorySegmentImpl dup(long offset, long size, int mask, MemoryScope scope) {
        return new MappedMemorySegmentImpl(min + offset, unmapper, size, mask, scope);
    }

    // mapped segment methods


    @Override
    public MappedMemorySegmentImpl asSlice(long offset, long newSize) {
        return (MappedMemorySegmentImpl)super.asSlice(offset, newSize);
    }

    @Override
    public MappedMemorySegmentImpl withAccessModes(int accessModes) {
        return (MappedMemorySegmentImpl)super.withAccessModes(accessModes);
    }

    @Override
    public void load() {
        nioAccess.load(min, unmapper.isSync(), length);
    }

    @Override
    public void unload() {
        nioAccess.unload(min, unmapper.isSync(), length);
    }

    @Override
    public boolean isLoaded() {
        return nioAccess.isLoaded(min, unmapper.isSync(), length);
    }

    @Override
    public void force() {
        nioAccess.force(unmapper.fileDescriptor(), min, unmapper.isSync(), 0, length);
    }

    // factories

    public static MappedMemorySegment makeMappedSegment(Path path, long bytesOffset, long bytesSize, FileChannel.MapMode mapMode) throws IOException {
        if (bytesSize < 0) throw new IllegalArgumentException("Requested bytes size must be >= 0.");
        if (bytesOffset < 0) throw new IllegalArgumentException("Requested bytes offset must be >= 0.");
        try (FileChannelImpl channelImpl = (FileChannelImpl)FileChannel.open(path, openOptions(mapMode))) {
            UnmapperProxy unmapperProxy = channelImpl.mapInternal(mapMode, bytesOffset, bytesSize);
            MemoryScope scope = MemoryScope.create(null, unmapperProxy::unmap);
            int modes = defaultAccessModes(bytesSize);
            if (mapMode == FileChannel.MapMode.READ_ONLY) {
                modes &= ~WRITE;
            }
            return new MappedMemorySegmentImpl(unmapperProxy.address(), unmapperProxy, bytesSize,
                    modes, scope);
        }
    }

    private static OpenOption[] openOptions(FileChannel.MapMode mapMode) {
        if (mapMode == FileChannel.MapMode.READ_ONLY) {
            return new OpenOption[] { StandardOpenOption.READ };
        } else if (mapMode == FileChannel.MapMode.READ_WRITE || mapMode == FileChannel.MapMode.PRIVATE) {
            return new OpenOption[] { StandardOpenOption.READ, StandardOpenOption.WRITE };
        } else {
            throw new UnsupportedOperationException("Unsupported map mode: " + mapMode);
        }
    }
}