package nanjeong.calendar;

import java.util.Scanner;

public class Sum {
	public static void main(String[] args) {
		System.out.println("두 수를 입력하세요.");
		
		Scanner scan = new Scanner(System.in);
		String s1, s2;
		s1 = scan.next();
		s2 = scan.next();
		int val1 = Integer.parseInt(s1);
		int val2 = Integer.parseInt(s2);
		System.out.printf("두 수의 합은 %d입니다.", val1 + val2);

		scan.close();
	}
}
