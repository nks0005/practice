
package java.io;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Objects;

import jdk.internal.util.ArraysSupport;

// 배열 스트림 출력
public class ByteArrayOutputStream extends OutputStream {

    protected byte buf[];
    protected int count;
    
    public ByteArrayOutputStream() {
        this(32);
    }

    // default 값 : 32
    public ByteArrayOutputStream(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Negative initial size: "
                                               + size);
        }
        buf = new byte[size];
    }

    // bof를 막기 위한 과정
    private void ensureCapacity(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = buf.length;
        int minGrowth = minCapacity - oldCapacity;
        if (minGrowth > 0) {
            buf = Arrays.copyOf(buf, ArraysSupport.newLength(oldCapacity,
                    minGrowth, oldCapacity /* preferred growth */));
        }
    }

    
    public synchronized void write(int b) {
        ensureCapacity(count + 1);
        buf[count] = (byte) b;
        count += 1;
    }


    public synchronized void write(byte b[], int off, int len) {
        Objects.checkFromIndexSize(off, len, b.length);
        ensureCapacity(count + len);
        System.arraycopy(b, off, buf, count, len);
        count += len;
    }


    public void writeBytes(byte b[]) {
        write(b, 0, b.length);
    }


    public synchronized void writeTo(OutputStream out) throws IOException {
        out.write(buf, 0, count);
    }

    public synchronized void reset() {
        count = 0;
    }

    public synchronized byte[] toByteArray() {
        return Arrays.copyOf(buf, count);
    }


    public synchronized int size() {
        return count;
    }

    public synchronized String toString() {
        return new String(buf, 0, count);
    }


    public synchronized String toString(String charsetName)
        throws UnsupportedEncodingException
    {
        return new String(buf, 0, count, charsetName);
    }

    public synchronized String toString(Charset charset) {
        return new String(buf, 0, count, charset);
    }


    @Deprecated
    public synchronized String toString(int hibyte) {
        return new String(buf, hibyte, 0, count);
    }

    public void close() throws IOException {
    }

}
