package AbstractWindowToolkit_EventTest;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class mainFrame extends Frame {
	Label location;

	mainFrame(String title) {
		super(title);
		location = new Label("Mouse Pointer Location : ");
		location.setSize(195, 15);
		location.setLocation(5, 30);
		location.setBackground(Color.yellow);
		add(location);

		super.addMouseMotionListener(new EventHandler());
		super.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				we.getWindow().setVisible(false);
				we.getWindow().dispose();
				System.exit(0);
			}
		});

		super.setSize(300, 200);
		super.setLayout(null);
		super.setVisible(true);
	}

	class EventHandler implements MouseMotionListener {
		public void mouseDragged(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
			location.setText("Mouse Pointer Location : (" + e.getX() + ", " + e.getY() + ")");
		}
	}
}

public class MouseEventTest {
	public static void main(String[] args) {
		new mainFrame("Mouse Event Test");
	}
}
