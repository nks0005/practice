package AbstractWindowToolkit;

import java.awt.*;

public class FileDialogTest {
	public static void main(String[] args) {
		Frame f = new Frame("Parent");
		f.setSize(300, 200);
		
		FileDialog fileOpen = new FileDialog(f, "파일 열기", FileDialog.LOAD);
		
		f.setVisible(true);
		fileOpen.setDirectory("c:\\");
		fileOpen.setVisible(true);
		
		System.out.println(fileOpen.getDirectory() + fileOpen.getFile());
	}
}
