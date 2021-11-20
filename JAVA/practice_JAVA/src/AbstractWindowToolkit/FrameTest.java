package AbstractWindowToolkit;

import java.awt.*;

public class FrameTest {
	public static void main(String args[]) {
		Frame f = new Frame("Hello"); // Frame은 Window의 자손이며, 매개변수는 제목
		f.setSize(300, 200);
		f.setVisible(true);
	}
}
