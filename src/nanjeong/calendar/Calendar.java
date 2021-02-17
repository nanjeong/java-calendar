package nanjeong.calendar;

import java.time.YearMonth;

public class Calendar {
	// 주어진 달이 몇 일까지 있는지 판단하는 메소드
	public int getMaxDaysOfMonth(int year, int month) {
		int[] commonYear = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		int[] leapYear = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
		YearMonth yearMonth = YearMonth.of(year, month);
		
		if (yearMonth.isLeapYear()) {
			return leapYear[month-1];
		}
		return commonYear[month-1];
	}

	// 주어진 달이 무슨 요일로 시작하는지 계산하는 메소드
	public int findDayOfWeek(int year, int month) {
		year -= 1;
		month -= 1;
		int sum = 365 * year;

		for (int i = month; i >= 1; i--) {
			sum += getMaxDaysOfMonth(year + 1, i);
		}

		int cntOfLeapYear = (int) (year / 4) - (int) (year / 100) + (int) (year / 400);
		sum += cntOfLeapYear;

		return sum % 7;
	}

}
