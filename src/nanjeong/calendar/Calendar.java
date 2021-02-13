package nanjeong.calendar;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	private boolean haveAPlan(int year, int month, int day, HashMap<String, ArrayList<String>> map) {
		if (day < 1) {
			return false;
		}
		
		String date = String.format("%04d-%02d-%02d", year, month, day);
		if (map.containsKey(date)) {
			return true;
		}
		return false;
	}
	public void printCalendar(int year, int month, HashMap<String, ArrayList<String>> map) {
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
				for(int j = i-7; j < i; j++) {
					if (haveAPlan(year, month, j, map)) {
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
	
	final String PROMPT = "> ";
	
	public String inputDate() {
		Scanner scan = new Scanner(System.in);
		System.out.println("날짜를 입력하세요.");
		System.out.print("YEAR> ");
		int year = scan.nextInt();
		System.out.print("MONTH> ");
		int month = scan.nextInt();
		System.out.print("DAY> ");
		int day = scan.nextInt();
		
		String date = String.format("%4d-%02d-%02d", year, month, day);
		
		return date;
	}
	
	public String inputPlan() {
		Scanner scan = new Scanner(System.in);
		System.out.println("일정을 입력하세요.");
		System.out.print(PROMPT);
		String plan = scan.nextLine();
		
		return plan;
	}
	
	public void searchPlan(HashMap<String, ArrayList<String>> map, String date) {
		int count = map.get(date).size();
		System.out.printf("%d개의 일정이 있습니다.\n", count);
		for (int i = 0; i < count; i++) {
			System.out.printf("%d. %s\n", i + 1, map.get(date).get(i));
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
	
	public boolean isRightDate(String date) {
		String[] splitedDate = date.split("-");
		int year = Integer.parseInt(splitedDate[0]);
		int month = Integer.parseInt(splitedDate[1]);
		int day = Integer.parseInt(splitedDate[2]);
		int maxDay = getMaxDaysOfMonth(year, month);
		
		if (year < 1 || month < 1 || month > 12 || day < 1 || day > maxDay) {
			return false;
		}
		
		return true;
	}
}
