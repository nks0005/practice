package AbstractWindowToolkit;

import java.awt.*;

public class TextAreaTest {
	public static void main(String args[]) {
		Frame f = new Frame("Login");
		f.setSize(400, 200);
		f.setLayout(new FlowLayout());
		
		TextArea comments = new TextArea("하고 싶은 말을 적으세요.", 10, 50);
		
		f.add(comments);
		comments.selectAll(); // TextArea 전체가 선택되게
		f.setVisible(true);
	}
}
