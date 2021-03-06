package InputOutput;

import java.io.*;
import java.util.Arrays;

public class ByteArrayStream {

	// ByteArray 기본 사용
	public static void Ex_1() {
		byte[] inSrc = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		byte[] outSrc = null;

		ByteArrayInputStream Bin = new ByteArrayInputStream(inSrc);
		ByteArrayOutputStream Bout = new ByteArrayOutputStream();

		PrintStream ps = System.out;
		int data = 0;

		while ((data = Bin.read()) != -1) {
			ps.println("binary data : " + Integer.toBinaryString(data));
			Bout.write(data);
		}

		outSrc = Bout.toByteArray();

		ps.println("outSrc : " + Arrays.toString(outSrc));
	}

	// 작업 효율을 높이기 위해
	public static void Ex_2() {
		byte[] inSrc = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		byte[] outSrc = null;

		byte[] temp = new byte[10];

		ByteArrayInputStream Bio = new ByteArrayInputStream(inSrc);
		ByteArrayOutputStream Bout = new ByteArrayOutputStream();

		Bio.read(temp, 0, temp.length); // temp 배열에 담음
		Bout.write(temp, 5, 5); // temp[5]째부터 5개를 가지고감

		outSrc = Bout.toByteArray();

		System.out.println("outSrc : " + Arrays.toString(outSrc));

	}

	public static void Ex_3() {
		byte[] inSrc = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		byte[] outSrc = null;

		byte[] temp = new byte[4];

		ByteArrayInputStream input = new ByteArrayInputStream(inSrc);
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try {
			while (input.available() > 0) {
				int len = input.read(temp);
				output.write(temp, 0, len);
			}
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		outSrc = output.toByteArray();

		System.out.println("outSrc : " + Arrays.toString(outSrc));
	}

	public static void Ex_4() {
		byte[] inSrc = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		byte[] outSrc = null;

		byte[] temp = new byte[4];

		ByteArrayInputStream bio = new ByteArrayInputStream(inSrc);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			while (bio.available() > 0) {
				int len = bio.read(temp);
				bout.write(temp, 0, len);
			}
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		outSrc = bout.toByteArray();

		System.out.println("outSrc : " + Arrays.toString(outSrc));

	}

	public static void main(String[] args) {
		// Ex_1();
		// Ex_2();
		// Ex_3();
		Ex_4();

	}
}
