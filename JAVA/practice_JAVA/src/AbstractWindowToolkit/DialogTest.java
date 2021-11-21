package AbstractWindowToolkit;

import java.awt.*;

public class DialogTest {
	public static void main(String[] args) {
		Frame f = new Frame("Parent");
		f.setSize(300, 200);

		// 부모 프레임은 f. modal을 true로 하여 필수 응답 Dialog로 만듬
		Dialog info = new Dialog(f, "Information", true);
		info.setSize(140, 90);
		info.setLocation(50, 50);
		info.setLayout(new FlowLayout());
		
		Label msg = new Label("This is modal Dialog", Label.CENTER);
		Button ok = new Button("OK");
		info.add(msg);
		info.add(ok);
		
		f.setVisible(true);
		info.setVisible(true);
	}
}
