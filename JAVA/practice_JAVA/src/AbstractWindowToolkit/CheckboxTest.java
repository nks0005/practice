package AbstractWindowToolkit;

import java.awt.*;

public class CheckboxTest {
	public static void main(String[] args) {
		Frame f = new Frame("Questions");
		f.setSize(305, 250);
		
		// Frame의 LayoutManager를 FlowLayout으로 설정
		f.setLayout(new FlowLayout());
		
		Label la_1 = new Label("그룹화하지 않은 체크박스");
		Checkbox cb1 = new Checkbox("첫번쨰", true);
		Checkbox cb2 = new Checkbox("두번쨰", false);
		
		f.add(la_1);
		f.add(cb1);
		f.add(cb2);
		
		Label la_2 = new Label("그룹화한 체크박스");
		CheckboxGroup cbg = new CheckboxGroup();
		Checkbox cbg_1 = new Checkbox("첫번째", cbg, true);
		Checkbox cbg_2 = new Checkbox("두번쨰", cbg, false);
		
		f.add(la_2);
		f.add(cbg_1);
		f.add(cbg_2);
		
		f.setVisible(true);
	}
}
