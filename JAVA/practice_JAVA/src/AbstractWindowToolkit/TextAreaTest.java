package AbstractWindowToolkit;

import java.awt.*;

public class TextAreaTest {
	public static void main(String args[]) {
		Frame f = new Frame("Login");
		f.setSize(400, 200);
		f.setLayout(new FlowLayout());
		
		TextArea comments = new TextArea("�ϰ� ���� ���� ��������.", 10, 50);
		
		f.add(comments);
		comments.selectAll(); // TextArea ��ü�� ���õǰ�
		f.setVisible(true);
	}
}
