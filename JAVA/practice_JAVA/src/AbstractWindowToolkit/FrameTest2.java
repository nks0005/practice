package AbstractWindowToolkit;

import java.awt.*;

public class FrameTest2 {
	public static void main(String[] args) {
		Frame f = new Frame("Login");
		f.setSize(300, 200);
		
		Toolkit tk = Toolkit.getDefaultToolkit(); // 구현된 Toolkit 객체를 구한다.
		Dimension screenSize = tk.getScreenSize(); // 화면 크기를 구한다.
		
		// 화면 크기 절반값 + Frame크기의 절반값 뺀 위치로하면 Frame은 화면 가운데에 위치한다.
		f.setLocation(screenSize.width/2 - 150, screenSize.height/2 - 100);
		f.setVisible(true);
	}
}
