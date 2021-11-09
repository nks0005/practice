/*
 * Jdk -> io file
 * 
 * 
 */

package InputOutput_Src;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;


// InputStream�� �ڼ�
public class SequenceInputStream extends InputStream {
    Enumeration<? extends InputStream> e; 
    InputStream in; 

    // Inputstream�� ����� ��ü���� ������ �Ű������� ����
    public SequenceInputStream(Enumeration<? extends InputStream> e) {
        this.e = e;
        peekNextStream();
    }

    // 2���� Stream ������ �޴´�.
    public SequenceInputStream(InputStream s1, InputStream s2) {
        Vector<InputStream> v = new Vector<>(2);
        v.addElement(s1);
        v.addElement(s2);
        e = v.elements();
        peekNextStream();
    }

    // ���� ���� �ִ� ��Ʈ���� �ݰ�, ���� ��Ʈ���� �򵵷� peekNextStream�� ȣ���Ѵ�
    final void nextStream() throws IOException {
        if (in != null) {
            in.close();
        }
        peekNextStream();
    }

    // ���� ��ü ���տ��� ó�� �Է� ��Ʈ���� �ִ´�.
    private void peekNextStream() {
        if (e.hasMoreElements()) {
            in = (InputStream) e.nextElement();
            if (in == null)
                throw new NullPointerException();
        } else {
            in = null;
        }
    }

    // ���� �Է� ��Ʈ���� �ִ��� ����
    public int available() throws IOException {
        if (in == null) {
            return 0; // no way to signal EOF from available()
        }
        return in.available();
    }

    // ���� �Է� ��Ʈ������ ���� read. ���� ������ ������ ���� ��Ʈ���� in�� �ִ´�.
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
