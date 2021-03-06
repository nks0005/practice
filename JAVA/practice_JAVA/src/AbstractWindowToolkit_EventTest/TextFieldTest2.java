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

		// 10개 글자를 입력할 수 있는 TextField 생성
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
				System.out.println("입력한 id가 유효하지 않습니다. 다시 입력해주세요.");

				tfId.requestFocus();
				tfId.selectAll();
			} else if (!password.equals("test")) {
				System.out.println("입력한 pwd가 유효하지 않습니다. 다시 입력해주세요.");

				tfPwd.requestFocus();
				tfPwd.selectAll();
			} else {
				System.out.println("성공적으로 로그인 하셨습니다.");
			}
		}
	}

	public static void main(String[] args) {
		TextFieldTest2 tf = new TextFieldTest2("Login");
	}
}
