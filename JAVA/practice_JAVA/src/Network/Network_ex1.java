package Network;

import java.net.*;
import java.util.*;

public class Network_ex1 {
	public static void main(String[] args) {
		InetAddress ip = null;
		InetAddress[] ipArr = null;

		try {
			/*
			 * getByName(String host) : �����θ�(host)�� ���� IP�ּҸ� ��´�. return static InetAddress
			 */
			ip = InetAddress.getByName("www.naver.com");
			System.out.println("getHostName() : " + ip.getHostName());
			System.out.println("getHostAddress() : " + ip.getHostAddress());
			System.out.println("toString() : " + ip.toString());

			/*
			 * getAddress() : IP�ּҸ� byte�迭�� ��ȯ�Ѵ�. return byte[]
			 */
			byte[] ipAddr = ip.getAddress();
			System.out.println("getAddress() : " + Arrays.toString(ipAddr)); // byte�� -128~127���̱⿡ �����÷ΰ� ��Ÿ��

			String result = "";
			for (int i = 0; i < ipAddr.length; i++) {
				result += (ipAddr[i] < 0) ? ipAddr[i] + 256 : ipAddr[i];
				if (i != ipAddr.length - 1)
					result += ".";
			}
			System.out.println("getAddress()+256 : " + result);
			System.out.println();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		try {
			/*
			 * getLocalHost() : ����ȣ��Ʈ�� IP�ּҸ� ��ȯ�Ѵ�. return static InetAddress
			 */
			ip = InetAddress.getLocalHost();
			System.out.println("getHostName() : " + ip.getHostName());
			System.out.println("getHostAddress() : " + ip.getHostAddress());
			System.out.println();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		try {
			ipArr = InetAddress.getAllByName("www.naver.com");

			for (int i = 0; i < ipArr.length; i++) {
				System.out.println("ipArr[" + i + "] : " + ipArr[i]);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
