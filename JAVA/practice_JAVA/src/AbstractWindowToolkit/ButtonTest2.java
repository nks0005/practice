package AbstractWindowToolkit;

import java.awt.*;

public class ButtonTest2 {
	public static void main(String[] args) {
		Frame f = new Frame("버튼 테스트 2");
		f.setSize(300, 200);
		f.setLayout(null); 
		
		Button b = new Button("확인");
		b.setSize(100, 50);
		b.setLocation(100, 75);
		
		f.add(b);
		f.setVisible(true);
	}
}
