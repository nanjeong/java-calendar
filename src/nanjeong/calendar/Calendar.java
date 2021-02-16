package nanjeong.calendar;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

public class Calendar {
	HashMap<LocalDate, ArrayList<String>> planMap = new HashMap<LocalDate, ArrayList<String>>();
	
	// 주어진 달이(몇 일)까지 있는 달인지 확인하는 메소드
	private boolean isDays(int month, int[] daysOfMonth) {
		for (int i = 0; i < daysOfMonth.length; i++) {
			if (month == daysOfMonth[i]) {
				return true;
			}
		}
		return false;
	}

	// 윤년이면 true, 아니면 false
	private boolean isLeapYear(int year) {
		if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
			return true;
		} else {
			return false;
		}
	}

	// 주어진 달이 몇 일까지 있는지 판단하는 메소드
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

	// 주어진 달이 무슨 요일로 시작하는지 계산하는 메소드
	private int findDayOfWeek(int year, int month) {
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

	// 일정이 있으면 true, 없으면 false
	private boolean haveAPlan(int year, int month, int day) {
		if (day < 1) return false;
		
		String strDate = String.format("%04d-%02d-%02d", year, month, day);
		LocalDate date = LocalDate.parse(strDate);
		
		if (planMap.containsKey(date)) return true;
		return false;
	}

	// 달력 출력 메소드
	public void printCalendar(Scanner scan) {
		System.out.println("[달력 보기]");
		
		int year = inputYear(scan);
		if (year == 0) return;
		
		int month = inputMonth(scan);
		if (month == 0) return;
		
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
				for (int j = i - 7; j < i; j++) {
					if (haveAPlan(year, month, j)) {
						System.out.printf("%3s", ".");
					} else {
						System.out.print("   ");
					}
				}
				System.out.println();
			}
			System.out.printf("%3d", i);
		}
		System.out.println("\n");
	}

	private int inputYear(Scanner scan) {
		int year = 0;
		
		try {
			do {
				System.out.print("YEAR> ");
				year = scan.nextInt();
			} while (year < 1);
		} catch (InputMismatchException e) {
			scan.nextLine();
			System.out.println("숫자만 입력해야합니다.");
		}
		
		return year;
	}
	
	private int inputMonth(Scanner scan) {
		int month  = 0;
		
		try {
			do {
				System.out.print("MONTH> ");
				month = scan.nextInt();
			} while (month < 1 || month > 12);
		} catch (InputMismatchException e) {
			scan.nextLine();
			System.out.println("숫자만 입력해야합니다.");
		}
		
		return month;
	}
	
	private int inputDay(int year, int month, Scanner scan) {
		int day = 0;
		int maxDay = getMaxDaysOfMonth(year, month);
		
		try {
			do {
				System.out.print("DAY> ");
				day = scan.nextInt();
			} while(day < 1 || day > maxDay);
		} catch (InputMismatchException e) {
			scan.nextLine();
			System.out.println("숫자만 입력해야합니다.");
		}
		
		return day;
	}

	public String inputDate(Scanner scan) {
		
		int year = inputYear(scan);
		if (year == 0) return "";
		
		int month = inputMonth(scan);
		if (month == 0) return "";
		
		int day = inputDay(year, month, scan);
		if (day == 0) return "";
		
		return String.format("%04d-%02d-%02d", year, month, day);
	}
	
	public void registPlan(Scanner scan) {
		System.out.println("[일정 등록]");
		
		String strDate = inputDate(scan);
		if ("".equals(strDate)) return;
		LocalDate date = LocalDate.parse(strDate);
		String plan = inputPlan(scan);
		
		if (planMap.containsKey(date)) {
			planMap.get(date).add(plan);
		} else {
			ArrayList<String> planList = new ArrayList<String>();
			planList.add(plan);
			planMap.put(date, planList);
		}
		
		System.out.println("일정이 등록되었습니다.");
		}
	// 일정 입력받는 메소드
	private String inputPlan(Scanner scan) {
		System.out.println("일정을 입력하세요.");
		System.out.print("> ");
		scan.nextLine();
		String plan = scan.nextLine();

		return plan;
	}

	// 일정 검색하는 메소드
	public void searchPlan(Scanner scan) {
		System.out.println("[일정 검색]");
		
		String strDate = inputDate(scan);
		if ("".equals(strDate)) return;
		LocalDate date = LocalDate.parse(strDate);
		
		if (planMap.get(date) == null) {
			System.out.println("일정이 없습니다.");
		} else {
			for (int i = 0; i < planMap.get(date).size(); i++) {
				System.out.printf("%d. %s\n", i + 1, planMap.get(date).get(i));
			}
		}
	}
	
	public void cancelPlan(Scanner scan) {
		System.out.println("[일정 취소]");
		
		String strDate = inputDate(scan);
		if ("".equals(strDate)) return;
		LocalDate date = LocalDate.parse(strDate);
		
		if (planMap.get(date) == null) {
			System.out.println("취소할 일정이 없습니다.");
		} else {
			int count = planMap.get(date).size();
			for (int i = 0; i < count; i++) {
				System.out.printf("%d. %s\n", i + 1, planMap.get(date).get(i));
			}
			System.out.println("\n취소하고 싶은 일정의 번호를 입력하세요.");
			System.out.print("> ");
			
			int idx = 0;
			try {
				idx = scan.nextInt();
			} catch (InputMismatchException e) {
				scan.nextLine();
				System.out.println("숫자만 입력해야합니다.");
				return;
			}
			
			if (idx < 0 || idx > count) {
				System.out.println("잘못된 입력입니다.");
			} else {
				planMap.get(date).remove(idx-1);
				if (planMap.get(date).size() == 0) {
					planMap.remove(date);
				}
				System.out.println("일정이 취소되었습니다.");
			}
		}
	}
	public void help() {
		System.out.println("+--------------------+");
		System.out.println("| 1. 일정 등록");
		System.out.println("| 2. 일정 검색");
		System.out.println("| 3. 일정 취소");
		System.out.println("| 4. 달력 보기");
		System.out.println("| h. 도움말 q. 종료");
		System.out.println("+--------------------+");
	}

}
