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
			// 포트 80번으로 bind 시킨다.
			serverSocket = new ServerSocket(80);
			System.out.println("서버가 시작되었습니다.");

			while (true) {
				// 클라이언트로부터 접속
				socket = serverSocket.accept();
				System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]에서 접속하였습니다.");

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
				sendToAll("#" + name + "님이 들어오셨습니다.");

				clients.put(name, out); // Key:name, out:value
				System.out.println("현재 서버 접속자 수는 " + clients.size() + "입니다.");

				while (in != null) {
					sendToAll(in.readUTF());
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				sendToAll("#" + name + "님이 나가셨습니다.");
				clients.remove(name);
				System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]에서 접속을 종료하였습니다.");
				System.out.println("현재 서버접속자 수는 " + clients.size() + "입니다.");
			}
		}
	}
}
