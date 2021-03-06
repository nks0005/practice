package InputOutput;

import java.io.*;

public class FileStream {
	static String file_1 = "source.java";
	static String file_2 = "target.java";

	public static void Ex_1() throws IOException {
		FileInputStream fis = new FileInputStream(file_1);
		int data = 0;
		while ((data = fis.read()) != -1) {
			char c = (char) data;
			System.out.print(c);
		}
	}

	// File Copy
	public static void Ex_2() throws IOException {
		FileInputStream fis = new FileInputStream(file_1);
		FileOutputStream fout = new FileOutputStream(file_2);

		int data = 0;
		while((data = fis.read()) != -1) {
			fout.write(data);
		}
		
		fis.close();
		fout.close();
	}

	public static void main(String args[]) {
		try {
			// Ex_1();
			Ex_2();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
}
