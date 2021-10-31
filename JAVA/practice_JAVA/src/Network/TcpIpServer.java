package Network;

import java.net.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TcpIpServer {
	// 현재 시간을 문자열로 변환하는 함수
	static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	}

	@SuppressWarnings("resource")
	public static void main(String args[]) {
		ServerSocket serverSocket = null;

		try {
			// 서버 소켓을 생성, 80번 포트와 결합 시킨다.
			serverSocket = new ServerSocket(80);
			System.out.println(getTime() + "서버가 준비되었습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				System.out.println(getTime() + "연결 요청을 기다립니다.");

				// 요청 대기 시간을 5초로 설정한다.
				// 5초동안 접속 요청이 없다면, SocketTimeoutException이 발생한다.
				serverSocket.setSoTimeout(5 * 1000);

				// 서버 소켓은 클라이언트의 연결 요청이 올 때까지 실행을 멈추고 계속 기다린다.
				// 클라이언트의 연결 요청이 오면, 클라이언트 소켓과 통신할 새로운 소켓을 생성한다.
				Socket socket = serverSocket.accept();

				System.out.println(getTime() + socket.getInetAddress() + "로 부터 연결 요청이 들어왔습니다.");
				System.out.println("socket.getPort() : " + socket.getPort());
				System.out.println("socket.getLocalPort() : " + socket.getLocalPort());

				// 소켓의 출력 스트림을 얻는다.
				OutputStream out = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(out);

				// 원격 소켓(remote socket)에 데이터를 보낸다.
				dos.writeUTF("[Notice] Test Message1 from Server." + getTime());
				System.out.println(getTime() + "데이터를 전송합니다.");

				// 스트림과 소켓을 닫아준다.
				dos.close();
				socket.close();
			} catch (SocketTimeoutException se) {
				System.out.println("지정된 시간 동안 접속 요청이 없어서 서버를 종료함");
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
