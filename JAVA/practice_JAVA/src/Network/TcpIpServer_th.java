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
			// 서버 소켓을 생성하여 80번 포트와 결합(bind) 시킨다.
			serverSocket = new ServerSocket(80);
			System.out.println(getTime() + "서버가 준비되었습니다.");

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
		// 5개의 쓰레드를 생성하는 서버를 생성
		TcpIpServer_th server = new TcpIpServer_th(5);
		server.start();
	}

	public void run() {
		while (true) {
			try {
				System.out.println(getTime() + "가 연결 요청을 기다린다.");

				Socket socket = serverSocket.accept();
				System.out.println(getTime() + socket.getInetAddress() + "로 부터 연결 요청이 들어왔습니다.");

				// 소켓의 출력 스트림을 얻는다.
				OutputStream out = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(out);

				// 원격 소켓 remote socket에 데이터를 보낸다.
				dos.writeUTF("[Notice] Test Message from Server");
				System.out.println(getTime() + "데이터를 전송했습니다.");

				// 스트림과 소켓을 닫아준다.
				dos.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
