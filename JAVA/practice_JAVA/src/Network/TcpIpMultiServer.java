package Network;

import java.net.*;
import java.io.*;
import java.util.*;

public class TcpIpMultiServer {
	HashMap clients;

	public TcpIpMultiServer() {
		// TODO Auto-generated constructor stub
		clients = new HashMap();
		Collections.synchronizedMap(clients);
	}

	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			// ��Ʈ 80������ bind ��Ų��.
			serverSocket = new ServerSocket(80);
			System.out.println("������ ���۵Ǿ����ϴ�.");

			while (true) {
				// Ŭ���̾�Ʈ�κ��� ����
				socket = serverSocket.accept();
				System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]���� �����Ͽ����ϴ�.");

				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendToAll(String msg) {
		Iterator it = clients.keySet().iterator();

		while (it.hasNext()) {
			try {
				DataOutputStream out = (DataOutputStream) clients.get(it.next());
				out.writeUTF(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		new TcpIpMultiServer().start();
	}

	class ServerReceiver extends Thread {
		Socket socket;
		DataInputStream in;
		DataOutputStream out;

		public ServerReceiver(Socket socket) {
			// TODO Auto-generated constructor stub
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch (IOException io) {
				io.printStackTrace();
			}
		}

		public void run() {
			String name = "";
			try {
				name = in.readUTF();
				sendToAll("#" + name + "���� �����̽��ϴ�.");

				clients.put(name, out); // Key:name, out:value
				System.out.println("���� ���� ������ ���� " + clients.size() + "�Դϴ�.");

				while (in != null) {
					sendToAll(in.readUTF());
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				sendToAll("#" + name + "���� �����̽��ϴ�.");
				clients.remove(name);
				System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]���� ������ �����Ͽ����ϴ�.");
				System.out.println("���� ���������� ���� " + clients.size() + "�Դϴ�.");
			}
		}
	}
}
