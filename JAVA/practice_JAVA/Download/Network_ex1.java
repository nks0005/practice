package Network;

import java.net.*;
import java.util.*;

public class Network_ex1 {
	public static void main(String[] args) {
		InetAddress ip = null;
		InetAddress[] ipArr = null;

		try {
			/*
			 * getByName(String host) : 도메인명(host)을 통해 IP주소를 얻는다. return static InetAddress
			 */
			ip = InetAddress.getByName("www.naver.com");
			System.out.println("getHostName() : " + ip.getHostName());
			System.out.println("getHostAddress() : " + ip.getHostAddress());
			System.out.println("toString() : " + ip.toString());

			/*
			 * getAddress() : IP주소를 byte배열로 반환한다. return byte[]
			 */
			byte[] ipAddr = ip.getAddress();
			System.out.println("getAddress() : " + Arrays.toString(ipAddr)); // byte는 -128~127값이기에 오버플로가 나타남

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
			 * getLocalHost() : 지역호스트의 IP주소를 반환한다. return static InetAddress
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
