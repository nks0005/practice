package InputOutput;
// ���� ��Ʈ�� �� Sequence ��Ʈ�� ����

// Sequence ���� ��Ʈ���� ���� ���� �Է��� �ϳ��� �����ְų� �ϳ��� �Է��� �������� ������

import java.io.*;
import java.util.*;

/*
 * SequenceInputStream(Enumeration e) - Enumeration�� ����� ������� �Է½�Ʈ���� �ϳ��� ��Ʈ������ ����
 * SequenceInputStream(InputStream s1, InputStream s2) - �� ���� �Է� ��Ʈ���� �ϳ��� ����
 */

public class SequenceStream {
	public static void main(String[] args) {
		byte[] outSrc = null;

		// ����Ʈ �迭�� Enumeration�� Vector�� �߰�
		Vector<ByteArrayInputStream> v = new Vector<ByteArrayInputStream>();
		v.add(new ByteArrayInputStream(new byte[] { 0, 1, 2 }));
		v.add(new ByteArrayInputStream(new byte[] { 3, 4, 5 }));
		v.add(new ByteArrayInputStream(new byte[] { 6, 7, 8 }));
		v.add(new ByteArrayInputStream(new byte[] { 9, 10, 11 }));

		SequenceInputStream Sin = new SequenceInputStream(v.elements());
		ByteArrayOutputStream Bout = new ByteArrayOutputStream();

		try {
			int data;
			while ((data = Sin.read()) != -1) {
				Bout.write(data);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		outSrc = Bout.toByteArray();
		System.out.println("outSrc : " + Arrays.toString(outSrc));
	}
}

/*
 * ����
 * https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=simpolor&
 * logNo=221066456034
 */