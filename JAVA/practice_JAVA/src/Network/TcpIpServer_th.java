package Network;

import java.net.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TcpIpServer_th implements Runnable {
	ServerSocket serverSocket;
	Thread[] threadArr;

	static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	}

	public TcpIpServer_th(int num) {
		try {
			// ���� ������ �����Ͽ� 80�� ��Ʈ�� ����(bind) ��Ų��.
			serverSocket = new ServerSocket(80);
			System.out.println(getTime() + "������ �غ�Ǿ����ϴ�.");

			threadArr = new Thread[num];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		for (int i = 0; i < threadArr.length; i++) {
			threadArr[i] = new Thread(this);
			threadArr[i].start();
		}
	}

	public static void main(String[] args) {
		// 5���� �����带 �����ϴ� ������ ����
		TcpIpServer_th server = new TcpIpServer_th(5);
		server.start();
	}

	public void run() {
		while (true) {
			try {
				System.out.println(getTime() + "�� ���� ��û�� ��ٸ���.");

				Socket socket = serverSocket.accept();
				System.out.println(getTime() + socket.getInetAddress() + "�� ���� ���� ��û�� ���Խ��ϴ�.");

				// ������ ��� ��Ʈ���� ��´�.
				OutputStream out = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(out);

				// ���� ���� remote socket�� �����͸� ������.
				dos.writeUTF("[Notice] Test Message from Server");
				System.out.println(getTime() + "�����͸� �����߽��ϴ�.");

				// ��Ʈ���� ������ �ݾ��ش�.
				dos.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
