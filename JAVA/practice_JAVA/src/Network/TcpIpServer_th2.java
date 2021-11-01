package Network;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TcpIpServer_th2 {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			// 서버 소켓을 생성하여 80번 포트와 bind 시킨다.
			serverSocket = new ServerSocket(80);
			System.out.println("서버가 시작되었습니다.");

			socket = serverSocket.accept();

			Sender sender = new Sender(socket);
			Receiver receiver = new Receiver(socket);

			sender.start();
			receiver.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Sender extends Thread {
	Socket socket;
	DataOutputStream out;
	String name;

	Sender(Socket socket) {
		this.socket = socket;
		try {
			out = new DataOutputStream(socket.getOutputStream());
			name = "[" + socket.getInetAddress() + ":" + socket.getPort() + "]";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		Scanner scanner = new Scanner(System.in);
		while (out != null) {
			try {
				out.writeUTF(name + scanner.nextLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class Receiver extends Thread {
	Socket socket;
	DataInputStream in;

	Receiver(Socket socket) {
		this.socket = socket;
		try {
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public void run() {
		while (in != null) {
			try {
				System.out.println(in.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}