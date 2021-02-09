package nanjeong.calendar;

public class Calendar {
	public static void main(String[] args) {
		String[] week = {"일", "월", "화", "수", "목", "금", "토"};
		for (int i = 0; i < 7; i++) {
			System.out.printf("%5s", week[i]);
		}
		System.out.print("\n----------------------");
		for (int i = 1; i <= 31; i++) {
			if ((i-1) % 7 == 0) {
				System.out.println();
			}
			System.out.printf("%3d", i);
		}
	}
}
