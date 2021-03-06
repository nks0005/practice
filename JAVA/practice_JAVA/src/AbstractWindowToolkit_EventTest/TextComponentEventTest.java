package AbstractWindowToolkit_EventTest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TextComponentEventTest extends Frame {
	TextField tf;
	TextArea ta;
	int colorCurrent;

	public TextComponentEventTest(String title) {
		super(title);

		// ScrollPane sp = new ScrollPane();
		colorCurrent = 0;
		tf = new TextField();
		ta = new TextArea();
		// sp.add(ta);
		super.add(ta, "Center"); // Border Layout
		super.add(tf, "South");

		tf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ta.append(tf.getText() + "\n");
				tf.setText("");
				tf.requestFocus();
				System.out.println("What is event? : " + e.getActionCommand().toString() + ", " + e.getID() + ", "
						+ e.getSource().toString() + ", " + e.getWhen());
				TextField tmp = (TextField) e.getSource();
				Color colorList[] = { Color.black, Color.red, Color.blue };

				if (!(colorCurrent < colorList.length)) {
					colorCurrent = 0;
				}

				tmp.setBackground(colorList[colorCurrent++]);
			}
		});

		super.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				we.getWindow().setVisible(false);
				we.getWindow().dispose();
				System.exit(0);
			}
		});

		ta.setEditable(false);
		super.setSize(300, 200);
		super.setVisible(true);
		tf.requestFocus();
	}

	public static void main(String[] args) {
		TextComponentEventTest tc = new TextComponentEventTest("Text Componenet Event Test");
	}
}
