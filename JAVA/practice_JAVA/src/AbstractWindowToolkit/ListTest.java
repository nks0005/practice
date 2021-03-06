package AbstractWindowToolkit;

import java.awt.*;

public class ListTest {
	public static void main(String[] args) {
		Frame f = new Frame("List 테스트");
		f.setSize(300, 200);
		f.setLayout(null);
		
		List selectOne = new List(6); // 6개 목록을 보여줄 수 있는 크기의 List를 만든다.
		selectOne.setLocation(20, 40);
		selectOne.setSize(100, 120);
		selectOne.add("Student");
		selectOne.add("Teacher");
		selectOne.add("Driver");
		selectOne.add("Computer Programmer");
		selectOne.add("Sales Man");
		selectOne.add("Musician");
		selectOne.add("Director");
		
		// 생성자와 두번째 인자값을 true로 설정해서 List의 목록에서 여러개를 선택 가능하게
		List selectMany = new List(6, true);
		selectMany.setLocation(150, 40);
		selectMany.setSize(100, 120);
		selectMany.add("Student");
		selectMany.add("Teacher");
		selectMany.add("Driver");
		selectMany.add("Computer Programmer");
		selectMany.add("Sales Man");
		selectMany.add("Musician");
		selectMany.add("Director");
		
		f.add(selectOne);
		f.add(selectMany);
		f.setVisible(true);
	}
}
