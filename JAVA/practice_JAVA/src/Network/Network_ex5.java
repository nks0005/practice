package Network;

import java.net.*;
import java.io.*;

public class Network_ex5 {
	public static void main(String[] args) {
		URL url = null;
		InputStream in = null;
		FileOutputStream out = null;
		String address = "https://raw.githubusercontent.com/nks0005/practice/master/JAVA/practice_JAVA/src/Network/Network_ex1.java";

		int ch = 0;

		try {
			url = new URL(address);

			/*
			 * URLConnection conn = url.openConnection(); InputStream in =
			 * conn.getInputStream();
			 */
			in = url.openStream();

			File f = new File("Download");
			if(!f.exists())
				f.mkdirs();
			
			out = new FileOutputStream("Download/Network_ex1.java");

			while ((ch = in.read()) != -1) {
				out.write(ch);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
