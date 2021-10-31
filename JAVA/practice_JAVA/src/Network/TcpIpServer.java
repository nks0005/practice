package Network;

import java.net.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TcpIpServer {
	// ���� �ð��� ���ڿ��� ��ȯ�ϴ� �Լ�
	static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	}

	@SuppressWarnings("resource")
	public static void main(String args[]) {
		ServerSocket serverSocket = null;

		try {
			// ���� ������ ����, 80�� ��Ʈ�� ���� ��Ų��.
			serverSocket = new ServerSocket(80);
			System.out.println(getTime() + "������ �غ�Ǿ����ϴ�.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				System.out.println(getTime() + "���� ��û�� ��ٸ��ϴ�.");

				// ��û ��� �ð��� 5�ʷ� �����Ѵ�.
				// 5�ʵ��� ���� ��û�� ���ٸ�, SocketTimeoutException�� �߻��Ѵ�.
				serverSocket.setSoTimeout(5 * 1000);

				// ���� ������ Ŭ���̾�Ʈ�� ���� ��û�� �� ������ ������ ���߰� ��� ��ٸ���.
				// Ŭ���̾�Ʈ�� ���� ��û�� ����, Ŭ���̾�Ʈ ���ϰ� ����� ���ο� ������ �����Ѵ�.
				Socket socket = serverSocket.accept();

				System.out.println(getTime() + socket.getInetAddress() + "�� ���� ���� ��û�� ���Խ��ϴ�.");
				System.out.println("socket.getPort() : " + socket.getPort());
				System.out.println("socket.getLocalPort() : " + socket.getLocalPort());

				// ������ ��� ��Ʈ���� ��´�.
				OutputStream out = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(out);

				// ���� ����(remote socket)�� �����͸� ������.
				dos.writeUTF("[Notice] Test Message1 from Server." + getTime());
				System.out.println(getTime() + "�����͸� �����մϴ�.");

				// ��Ʈ���� ������ �ݾ��ش�.
				dos.close();
				socket.close();
			} catch (SocketTimeoutException se) {
				System.out.println("������ �ð� ���� ���� ��û�� ��� ������ ������");
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
