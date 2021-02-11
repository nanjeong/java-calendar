package nanjeong.calendar;

public class Calendar {
	// 주어진 달이(몇 일)까지 있는 달인지 확인하는 메소드
	private boolean isDays(int month, int[] days) {
		for (int i = 0; i < days.length; i++) {
			if (month == days[i]) {
				return true;
			}
		}

		return false;
	}
	
	private boolean isLeapYear(int year) {
		if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
			return true;
		} else {
			return false;
		}
	}
	// 주어진 달이 최대 몇 일까지 있는지 판단하는 메소드
	private int getMaxDaysOfMonth(int year, int month) {
		int[] days31 = { 1, 3, 5, 7, 8, 10, 12 };
		int[] days30 = { 4, 6, 9, 11 };

		if (isDays(month, days31)) {
			return 31;
		} else if (isDays(month, days30)) {
			return 30;
		} else {
			if (isLeapYear(year)) {
				return 29;
			}
			return 28;
		}
	}

	private int findDayOfWeek(int year, int month) {
		year -= 1;
		month -= 1;
		int sum = 365 * year;
		
		for (int i = month; i >= 1; i--) {
			sum += getMaxDaysOfMonth(year+1, i);
		}
		
		int cntOfLeapYear = (int)(year/4) - (int)(year/100) + (int)(year/400);
		sum += cntOfLeapYear;
		
		return sum % 7;
	}
	
	public void printCalendar(int year, int month) {
		int days = getMaxDaysOfMonth(year, month);
		
		System.out.printf("\n    <<%4d년%3d월>>\n", year, month);
		String[] week = { "일", "월", "화", "수", "목", "금", "토" };
		for (int i = 0; i < 7; i++) {
			System.out.printf("%5s", week[i]);
		}
		System.out.println("\n----------------------");
		
		int space = findDayOfWeek(year, month);
		
		if (space != 6) {
			for (int i = 0; i <= space; i++) {
				System.out.print("   ");
			}
		}
		
		for (int i = 1; i <= days; i++) {
			if ((i + space) % 7 == 0 && i != 1) {
				System.out.println();
			}
			System.out.printf("%3d", i);
		}
	}
	
}
