package AbstractWindowToolkit;

import java.awt.*;

public class CanvasTest {
	public static void main(String[] args) {
		Frame f = new Frame("Canvas test");
		f.setSize(300, 200);
		f.setLayout(null);
		
		Canvas c = new Canvas();
		c.setBackground(Color.pink);
		c.setBounds(50, 50, 150, 100);
		
		f.add(c);
		f.setVisible(true);
	}
}
