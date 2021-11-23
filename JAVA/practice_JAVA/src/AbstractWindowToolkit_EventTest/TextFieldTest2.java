package AbstractWindowToolkit_EventTest;

import java.awt.*;
import java.awt.event.*;

public class TextFieldTest2 extends Frame {
	Label lid;
	Label lpwd;
	TextField tfId;
	TextField tfPwd;
	Button ok;

	TextFieldTest2(String title) {
		super(title);
		super.setLayout(new FlowLayout());
		super.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().setVisible(false);
				e.getWindow().dispose();
				System.exit(0);
			}
		});

		lid = new Label("ID : ", Label.RIGHT);
		lpwd = new Label("Password : ", Label.RIGHT);

		// 10�� ���ڸ� �Է��� �� �ִ� TextField ����
		tfId = new TextField(10);
		tfPwd = new TextField(10);
		tfPwd.setEchoChar('*');

		ok = new Button("ok");
		ok.addActionListener(new EventHandler());

		super.add(lid);
		super.add(tfId);
		super.add(lpwd);
		super.add(tfPwd);
		super.add(ok);

		super.setSize(450, 65);
		super.setVisible(true);
	}

	class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String id = tfId.getText();
			String password = tfPwd.getText();
			if (!id.equals("java")) {
				System.out.println("�Է��� id�� ��ȿ���� �ʽ��ϴ�. �ٽ� �Է����ּ���.");

				tfId.requestFocus();
				tfId.selectAll();
			} else if (!password.equals("test")) {
				System.out.println("�Է��� pwd�� ��ȿ���� �ʽ��ϴ�. �ٽ� �Է����ּ���.");

				tfPwd.requestFocus();
				tfPwd.selectAll();
			} else {
				System.out.println("���������� �α��� �ϼ̽��ϴ�.");
			}
		}
	}

	public static void main(String[] args) {
		TextFieldTest2 tf = new TextFieldTest2("Login");
	}
}