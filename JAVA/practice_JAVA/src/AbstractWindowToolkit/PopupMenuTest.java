package AbstractWindowToolkit;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopupMenuTest {
	public static void main(String args[]) {
		final Frame f = new Frame("PopupMenu test");
		f.setSize(300, 200);

		final PopupMenu pMenu = new PopupMenu("Edit");
		MenuItem miCut = new MenuItem("Cut");
		MenuItem miCopy = new MenuItem("Copy");
		MenuItem miPaste = new MenuItem("Paste");
		pMenu.add(miCut);
		pMenu.add(miCopy);
		pMenu.add(miPaste);

		f.add(pMenu);
		f.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK) {
					//pMenu.show(f, me.getX(), me.getY());
					
					Canvas r = new Canvas();
					r.setBackground(Color.white);
					r.setBounds(me.getX()-5, me.getY()-5, 10, 10);
					
					f.add(r);
					System.out.println("remove : " + me.getX() + ", " + me.getY());
				}
				else if(me.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK) {
					final Canvas c = new Canvas();
					c.setBackground(Color.pink);
					c.setBounds(me.getX()-5, me.getY()-5, 10, 10);
					c.addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent me) {
							if(me.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK) {
								f.remove((Canvas)me.getSource());
								System.out.println("Remove " + c.getName());
							}
						}
					});
					
					f.add(c);
					System.out.println("print : " + me.getX() + ", " + me.getY());
				}
			}
		});
		
		f.setVisible(true);
	}
}
