/*
 * Jdk -> io file
 * 
 * 
 */

package InputOutput_Src;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;


// InputStream의 자손
public class SequenceInputStream extends InputStream {
    Enumeration<? extends InputStream> e; 
    InputStream in; 

    // Inputstream을 상속한 객체들의 집합을 매개변수로 받음
    public SequenceInputStream(Enumeration<? extends InputStream> e) {
        this.e = e;
        peekNextStream();
    }

    // 2개의 Stream 변수를 받는다.
    public SequenceInputStream(InputStream s1, InputStream s2) {
        Vector<InputStream> v = new Vector<>(2);
        v.addElement(s1);
        v.addElement(s2);
        e = v.elements();
        peekNextStream();
    }

    // 현재 쓰고 있는 스트림을 닫고, 다음 스트림을 얻도록 peekNextStream을 호출한다
    final void nextStream() throws IOException {
        if (in != null) {
            in.close();
        }
        peekNextStream();
    }

    // 받은 객체 집합에서 처음 입력 스트림을 넣는다.
    private void peekNextStream() {
        if (e.hasMoreElements()) {
            in = (InputStream) e.nextElement();
            if (in == null)
                throw new NullPointerException();
        } else {
            in = null;
        }
    }

    // 한재 입력 스트림이 있는지 여부
    public int available() throws IOException {
        if (in == null) {
            return 0; // no way to signal EOF from available()
        }
        return in.available();
    }

    // 현재 입력 스트림으로 부터 read. 만약 에러가 나오면 다음 스트림을 in에 넣는다.
    public int read() throws IOException {
        while (in != null) {
            int c = in.read();
            if (c != -1) {
                return c;
            }
            nextStream();
        }
        return -1;
    }


    public int read(byte b[], int off, int len) throws IOException {
        if (in == null) {
            return -1;
        } else if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }
        do {
            int n = in.read(b, off, len);
            if (n > 0) {
                return n;
            }
            nextStream();
        } while (in != null);
        return -1;
    }

    
    public void close() throws IOException {
        IOException ioe = null;
        while (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                if (ioe == null) {
                    ioe = e;
                } else {
                    ioe.addSuppressed(e);
                }
            }
            peekNextStream();
        }
        if (ioe != null) {
            throw ioe;
        }
    }
}
