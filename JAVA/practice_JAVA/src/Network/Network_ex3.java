package Network;

import java.net.*;
import java.io.*;

public class Network_ex3 {
	public static void main(String args[]) {
		URL url = null;
		String address = "https://search.shopping.naver.com/search/category?catId=50000450";
		String line = "";

		try {
			url = new URL(address);
			URLConnection conn = url.openConnection();

			System.out.println("conn.toString() : " + conn.toString());
			// UserInteraction�� ��� ����
			System.out.println("getAllowUserInteraction() : " + conn.getAllowUserInteraction());
			// ���� ���� �ð��� ms�� ��ȯ
			System.out.println("getConnectTimeout() : " + conn.getConnectTimeout());
			// content�� ���ڵ��� ��ȯ
			System.out.println("getContentEncoding() : " + conn.getContentEncoding());
			// content�� type�� ��ȯ
			System.out.println("getContentType() : " + conn.getContentType());
			System.out.println("getDate() : " + conn.getDate());
			System.out.println("getDefaultAllowUserInteraction() : " + conn.getDefaultAllowUserInteraction());
			System.out.println("getDefaultUseCaches() : " + conn.getDefaultUseCaches());
			System.out.println("getDoInput() : " + conn.getDoInput());
			System.out.println("getDoOutput() : " + conn.getDoOutput());
			System.out.println("getExpiration() : " + conn.getExpiration());
			System.out.println("getHeaderFields() : " + conn.getHeaderFields());
			System.out.println("getIfModifiedSince() : " + conn.getIfModifiedSince());
			System.out.println("getLastModified() : " + conn.getLastModified());
			System.out.println("getReadTimeout() : " + conn.getReadTimeout());
			System.out.println("getURL() : " + conn.getURL());
			System.out.println("getUseCaches() : " + conn.getUseCaches());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
