package InputOutput;

import java.io.*;
import java.util.Arrays;

public class ByteArrayStream {
	public static void main(String[] args) {
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
}
