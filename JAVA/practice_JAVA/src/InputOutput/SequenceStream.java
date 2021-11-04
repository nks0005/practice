package InputOutput;
// 보조 스트림 중 Sequence 스트림 예제

// Sequence 보조 스트림은 여러 개의 입력을 하나로 합쳐주거나 하나의 입력을 여러개로 갈라줌

import java.io.*;
import java.util.*;

/*
 * SequenceInputStream(Enumeration e) - Enumeration에 저장된 순서대로 입력스트림을 하나의 스트림으로 연결
 * SequenceInputStream(InputStream s1, InputStream s2) - 두 개의 입력 스트림을 하나로 연결
 */

public class SequenceStream {
	public static void main(String[] args) {
		byte[] outSrc = null;

		// 바이트 배열을 Enumeration인 Vector에 추가
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
 * 참조
 * https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=simpolor&
 * logNo=221066456034
 */