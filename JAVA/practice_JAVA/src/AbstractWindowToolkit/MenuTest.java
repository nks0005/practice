package AbstractWindowToolkit;

import java.awt.*;

public class MenuTest {
	public static void main(String[] args) {
		Frame f = new Frame("Frame with Menu");
		f.setSize(300, 200);

		MenuBar mb = new MenuBar();

		Menu mFile = new Menu("File");
		MenuItem miNew = new MenuItem("New");
		MenuItem miOpen = new MenuItem("Open");
		Menu mOthers = new Menu("Others");
		MenuItem miExit = new MenuItem("Exit");

		mFile.add(miNew);
		mFile.add(miOpen);
		mFile.add(mOthers);
		mFile.addSeparator();
		mFile.add(miExit);

		MenuItem miPrint = new MenuItem("Print");
		MenuItem miPreview = new MenuItem("Preview");
		MenuItem miSetup = new MenuItem("Setup");

		mOthers.add(miPrint);
		mOthers.add(miPreview);
		mOthers.add(miSetup);

		Menu mEdit = new Menu("Edit");
		Menu mView = new Menu("View");
		CheckboxMenuItem miStatusbar = new CheckboxMenuItem("Statusbar");
		mView.add(miStatusbar);
		Menu mHelp = new Menu("Help");

		mb.add(mFile);
		mb.add(mEdit);
		mb.add(mView);
		mb.add(mHelp);

		f.setMenuBar(mb);
		f.setVisible(true);
	}
}
