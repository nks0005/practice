package Network;

import java.net.*;
import java.io.*;

public class Network_ex4 {
	public static void main(String[] args) {
		URL url = null;
		BufferedReader input = null;
		
		String address = "https://github.com/nks0005/practice/tree/master/JAVA/practice_JAVA/src/Network";
		String line = "";
		
		try {
			url = new URL(address);
			
			input = new BufferedReader(new InputStreamReader(url.openStream()));
			
			while((line=input.readLine()) != null) {
				System.out.println(line);
			}
			input.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
