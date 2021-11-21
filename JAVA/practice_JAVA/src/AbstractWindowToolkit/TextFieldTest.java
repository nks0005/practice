package AbstractWindowToolkit;

import java.awt.*;

public class TextFieldTest {
	public static void main(String[] args) {
		Frame f = new Frame("Login");
		f.setSize(400, 65);
		f.setLayout(new FlowLayout());
		
		Label lid = new Label("ID : ", Label.RIGHT); // 우측 정렬
		Label lpwd = new Label("Password : ", Label.RIGHT);
		
		TextField id = new TextField(10);
		TextField pwd = new TextField(10);
		pwd.setEchoChar('*'); // 입력값 대신 *
		
		f.add(lid);
		f.add(id);
		f.add(lpwd);
		f.add(pwd);
		f.setVisible(true);
	}
}
