package AbstractWindowToolkit;

import java.awt.*;

public class LabelTest {
	public static void main(String[] args){
		Frame f = new Frame("Label 테스트");
		f.setSize(300, 200);
		f.setLayout(null);
		
		Label id = new Label("ID : ");
		id.setBounds(50, 50, 30, 10);
		
		Label pwd = new Label("pwd : ");
		pwd.setBounds(50, 65, 100, 15); // 50, 65 위치에 크기가 100, 10
		
		f.add(id);
		f.add(pwd);
		f.setVisible(true);
	}
}
