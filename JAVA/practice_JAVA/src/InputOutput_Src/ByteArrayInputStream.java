
package java.io;

import java.util.Arrays;
import java.util.Objects;


// 바이트 배열 스트림 
public class ByteArrayInputStream extends InputStream {
    protected byte buf[]; // 바이트 배열
    protected int pos; // 현재 위치
    protected int mark = 0;
    protected int count; // 바이트 크기

    
    public ByteArrayInputStream(byte buf[]) {
        this.buf = buf;
        this.pos = 0;
        this.count = buf.length;
    }

    
    public ByteArrayInputStream(byte buf[], int offset, int length) {
        this.buf = buf;
        this.pos = offset;
        this.count = Math.min(offset + length, buf.length);
        this.mark = offset;
    }


    // 현재 배열 위치가 배열 총 크기보다 작은 경우, 해당 byte를 반환
    public synchronized int read() {
        return (pos < count) ? (buf[pos++] & 0xff) : -1;
    }


    public synchronized int read(byte b[], int off, int len) {
        Objects.checkFromIndexSize(off, len, b.length);

        if (pos >= count) {
            return -1;
        }

        int avail = count - pos;
        if (len > avail) {
            len = avail;
        }
        if (len <= 0) {
            return 0;
        }
        System.arraycopy(buf, pos, b, off, len);
        pos += len;
        return len;
    }

    public synchronized byte[] readAllBytes() {
        byte[] result = Arrays.copyOfRange(buf, pos, count);
        pos = count;
        return result;
    }

    public int readNBytes(byte[] b, int off, int len) {
        int n = read(b, off, len);
        return n == -1 ? 0 : n;
    }

    
    public synchronized long transferTo(OutputStream out) throws IOException {
        int len = count - pos;
        out.write(buf, pos, len);
        pos = count;
        return len;
    }

    public synchronized long skip(long n) {
        long k = count - pos;
        if (n < k) {
            k = n < 0 ? 0 : n;
        }

        pos += k;
        return k;
    }

    public synchronized int available() {
        return count - pos;
    }

    public boolean markSupported() {
        return true;
    }

    public void mark(int readAheadLimit) {
        mark = pos;
    }

    public synchronized void reset() {
        pos = mark;
    }

    public void close() throws IOException {
    }

}
