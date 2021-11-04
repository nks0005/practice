package InputOutput;

import java.io.*;
import java.util.Arrays;

public class ByteArrayStream {

	// ByteArray �⺻ ���
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

	// �۾� ȿ���� ���̱� ����
	public static void Ex_2() {
		byte[] inSrc = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		byte[] outSrc = null;
		
		byte[] temp = new byte[10];
		
		ByteArrayInputStream Bio = new ByteArrayInputStream(inSrc);
		ByteArrayOutputStream Bout = new ByteArrayOutputStream();
		
		Bio.read(temp, 0, temp.length); // temp �迭�� ����
		Bout.write(temp, 5, 5); // temp[5]°���� 5���� ������
		
		outSrc = Bout.toByteArray();
		
		System.out.println("outSrc : " + Arrays.toString(outSrc));
		
	}

	public static void main(String[] args) {
		// Ex_1();
		Ex_2();
	}
}
