package AbstractWindowToolkit_EventTest;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditor extends Frame {
	String fileName;
	TextArea content;
	MenuBar mb;
	Menu mFile;
	MenuItem miNew, miOpen, miSaveAs, miExit;

	public static void main(String[] args) {
		TextEditor te = new TextEditor("TextEditor");
	}

	public TextEditor(String title) {
		super(title);
		super.setSize(300, 200);
		super.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				we.getWindow().setVisible(false);
				we.getWindow().dispose();
				System.exit(0);
			}
		});

		content = new TextArea();
		super.add(content);

		mb = new MenuBar();
		mFile = new Menu("File");
		miHandler mihandler = new miHandler();
		miNew = new MenuItem("New");
		miNew.addActionListener(mihandler);
		miOpen = new MenuItem("Open");
		miOpen.addActionListener(mihandler);
		miSaveAs = new MenuItem("SaveAs");
		miSaveAs.addActionListener(mihandler);
		miExit = new MenuItem("Exit");
		miExit.addActionListener(mihandler);

		mFile.add(miNew);
		mFile.add(miOpen);
		mFile.add(miSaveAs);
		mFile.addSeparator();
		mFile.add(miExit);
		mb.add(mFile);
		super.setMenuBar(mb);

		super.setVisible(true);
	}

	void openFile(String fileName) {
		FileReader fr;
		BufferedReader br;
		StringWriter sw;

		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			sw = new StringWriter();

			int ch = 0;
			while ((ch = br.read()) != -1) {
				sw.write(ch);
			}

			content.setText(sw.toString());

			br.close();
			fr.close();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	void saveFile(String fileName) {
		FileWriter fw;
		BufferedWriter bw;

		try {
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);

			bw.write(content.getText());

			bw.close();
			fw.close();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	class miHandler implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			String command = ae.getActionCommand();

			if (command.equals("New")) {
				content.setText("");
			} else if (command.equals("Open")) {
				FileDialog fd = new FileDialog(TextEditor.this, "???? ????", FileDialog.LOAD);
				fd.setVisible(true);

				fileName = fd.getDirectory() + fd.getFile();
				openFile(fileName);
			} else if (command.equals("SaveAs")) {
				FileDialog fd = new FileDialog(TextEditor.this, "???? ????", FileDialog.SAVE);
				fd.setVisible(true);

				fileName = fd.getDirectory() + fd.getFile();
				saveFile(fileName);
			} else if (command.equals("Exit")) {
				System.exit(0);
			}
		}
	}
}
