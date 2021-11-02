package Network;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

public class UdpServer {
	public void start() throws IOException {
		// ��Ʈ 80���� ����ϴ� ������ ����
		DatagramSocket socket = new DatagramSocket(80);
		DatagramPacket inPacket, outPacket;

		byte[] inMsg = new byte[10];
		byte[] outMsg;

		while (true) {
			// �����͸� �����ϱ� ���� ��Ŷ�� ����
			inPacket = new DatagramPacket(inMsg, inMsg.length);

			// ��Ŷ�� ���� �����͸� ����
			socket.receive(inPacket);

			// ������ ��Ŷ���� ���� client�� IP�ּҿ� Port�� ��´�
			InetAddress address = inPacket.getAddress();
			int port = inPacket.getPort();

			// ������ ���� �ð��� �ú��� ����([hh:mm:ss])�� ��ȯ�Ѵ�.
			SimpleDateFormat sdf = new SimpleDateFormat("[hh:mm:ss]");
			String time = sdf.format(new Date());
			outMsg = time.getBytes();

			// ��Ŷ�� �����ؼ� client���� ����
			outPacket = new DatagramPacket(outMsg, outMsg.length, address, port);
			socket.send(outPacket);
		}
	}

	public void main(String args[]) {
		try {
			new UdpServer().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
