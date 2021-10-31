package Network;

import java.net.*;
import java.io.*;

public class TcpIpClient {
	public static void main(String args[]) {
		try {
			String serverIp = "127.0.0.1";

			System.out.println("������ ���� �� �Դϴ�. ���� IP : " + serverIp);
			
			// ������ �����Ͽ� ������ ��û�Ѵ�
			Socket socket = new Socket(serverIp, 80);

			// ������ �Է½�Ʈ���� ��´�.
			InputStream in = socket.getInputStream();
			DataInputStream dis = new DataInputStream(in);
			
			// �������� ���� ���� �����͸� ����Ѵ�.
			System.out.println("�����κ��� ���� �޽��� : " + dis.readUTF());
			System.out.println("������ �����Ѵ�.");
			
			// ��Ʈ���� ������ �ݴ´�.
			dis.close();
			socket.close();
			System.out.println("������ ����Ǿ���.");
			
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
