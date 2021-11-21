package AbstractWindowToolkit;

import java.awt.*;

public class CheckboxTest {
	public static void main(String[] args) {
		Frame f = new Frame("Questions");
		f.setSize(305, 250);
		
		// Frame�� LayoutManager�� FlowLayout���� ����
		f.setLayout(new FlowLayout());
		
		Label la_1 = new Label("�׷�ȭ���� ���� üũ�ڽ�");
		Checkbox cb1 = new Checkbox("ù����", true);
		Checkbox cb2 = new Checkbox("�ι���", false);
		
		f.add(la_1);
		f.add(cb1);
		f.add(cb2);
		
		Label la_2 = new Label("�׷�ȭ�� üũ�ڽ�");
		CheckboxGroup cbg = new CheckboxGroup();
		Checkbox cbg_1 = new Checkbox("ù��°", cbg, true);
		Checkbox cbg_2 = new Checkbox("�ι���", cbg, false);
		
		f.add(la_2);
		f.add(cbg_1);
		f.add(cbg_2);
		
		f.setVisible(true);
	}
}