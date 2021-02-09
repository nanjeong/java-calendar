package nanjeong.calendar;

import java.util.Scanner;

public class Calendar {
	// 주어진 달이(몇 일)까지 있는 달인지 확인하는 메소드
	private boolean IsDays(int month, int[] days) {
		for (int i = 0; i < days.length; i++) {
			if (month == days[i]) {
				return true;
			}
		}

		return false;
	}

	// 주어진 달이 최대 몇 일까지 있는지 판단하는 메소드
	private int getMaxDaysOfMonth(int month) {
		int[] days31 = { 1, 3, 5, 7, 8, 10, 12 };
		int[] days30 = { 4, 6, 9, 11 };

		if (IsDays(month, days31)) {
			return 31;
		} else if (IsDays(month, days30)) {
			return 30;
		} else {
			return 28;
		}
	}

	public void printSampleCalendar() {
		String[] week = { "일", "월", "화", "수", "목", "금", "토" };
		for (int i = 0; i < 7; i++) {
			System.out.printf("%5s", week[i]);
		}
		System.out.print("\n----------------------");
		for (int i = 1; i <= 31; i++) {
			if ((i - 1) % 7 == 0) {
				System.out.println();
			}
			System.out.printf("%3d", i);
		}
	}

	public static void main(String[] args) {
		// 달을 입력받아 최대 일수 출력하기
		System.out.println("달을 입력하세요.");

		Scanner scan = new Scanner(System.in);
		Calendar cal = new Calendar();
		int month = scan.nextInt();

		if (month < 1 || month > 12) {
			System.out.printf("%d월은 존재하지 않습니다.", month);
		} else {
			System.out.printf("%d월은 %d일까지 있습니다.", month, cal.getMaxDaysOfMonth(month));
		}

		scan.close();
	}
}
