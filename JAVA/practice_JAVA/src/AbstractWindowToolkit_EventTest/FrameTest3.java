package AbstractWindowToolkit_EventTest;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FrameTest3 {
	public static void main(String[] args) {
		Frame f = new Frame("Login");
		f.setSize(300, 200);
		
		f.addWindowListener(new EventHandler());
		f.setVisible(true);
	}
}

class EventHandler implements WindowListener {
	public void windowOpened(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) { // Frame ?ݱ? ??ư ȣ??
		e.getWindow().setVisible(false);
		e.getWindow().dispose();
		System.exit(0);
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

}