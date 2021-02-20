package nanjeong.calendar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;

public class PlanItem extends Calendar{
	HashMap<LocalDate, ArrayList<String>> planMap = new HashMap<LocalDate, ArrayList<String>>();
	
	public void initPlanMap() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		try {
			BufferedReader planFile = new BufferedReader(new FileReader("c:/thisisjava/workspace/calendar/plan.txt"));
			while (true) {
				String strDate = planFile.readLine();
				if (strDate == null) break;
				String plan = planFile.readLine();
				
				LocalDate date = LocalDate.parse(strDate, formatter);
				
				if (planMap.containsKey(date)) {
					planMap.get(date).add(plan);
				} else {
					ArrayList<String> planList = new ArrayList<String>();
					planList.add(plan);
					planMap.put(date, planList);
				}
			}
			planFile.close();
		} catch (IOException e) {
			System.out.println("파일을 읽어오는데 오류가 발생했습니다.");
		}
	}
	
	public int inputYear(Scanner scan) {
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
	
	public int inputMonth(Scanner scan) {
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
	
	public int inputDay(int year, int month, Scanner scan) {
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
	
	public void registPlan(Scanner scan){
		System.out.println("[일정 등록]");
		
		String strDate = inputDate(scan);
		if ("".equals(strDate)) return;
		LocalDate date = LocalDate.parse(strDate);
		String plan = inputPlan(scan);
		updateFile(strDate, plan);
		
		if (planMap.containsKey(date)) {
			planMap.get(date).add(plan);
		} else {
			ArrayList<String> planList = new ArrayList<String>();
			planList.add(plan);
			planMap.put(date, planList);
		}
		
		System.out.println("일정이 등록되었습니다.");
	}
	
	private void updateFile(String strDate, String strPlan) {
		try {
			FileWriter planWrite = new FileWriter("c:/thisisjava/workspace/calendar/plan.txt", true);
			String date = strDate + "\r\n";
			planWrite.write(date);
			String plan = strPlan + "\r\n";
			planWrite.write(plan);
			
			planWrite.close();
		} catch (IOException e) {
			System.out.println("파일에 작성하는데 오류가 발생했습니다.");
		}
	}
	
	private String inputPlan(Scanner scan) {
		System.out.println("일정을 입력하세요.");
		System.out.print("> ");
		scan.nextLine();
		String plan = scan.nextLine();

		return plan;
	}
	
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
	
	private boolean haveAPlan(int year, int month, int day) {
		if (day < 1) return false;
		
		String strDate = String.format("%04d-%02d-%02d", year, month, day);
		LocalDate date = LocalDate.parse(strDate);
		
		if (planMap.containsKey(date)) return true;
		return false;
	}

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
	
	public void help() {
		System.out.println("+--------------------+");
		System.out.println("| 1. 일정 등록");
		System.out.println("| 2. 일정 검색");
		System.out.println("| 3. 일정 취소");
		System.out.println("| 4. 달력 보기");
		System.out.println("| h. 도움말 q. 종료");
		System.out.println("+--------------------+");
	}
	
	public void updateFile() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("c:/thisisjava/workspace/calendar/plan.txt"));
			
			Iterator<HashMap.Entry<LocalDate, ArrayList<String>>> iteratorE = planMap.entrySet().iterator();
			while (iteratorE.hasNext()) {
				HashMap.Entry<LocalDate, ArrayList<String>> entry = (HashMap.Entry<LocalDate, ArrayList<String>>) iteratorE.next();
				LocalDate date = entry.getKey();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String strDate = date.format(formatter);
				ArrayList<String> plan = entry.getValue();
				for (String strPlan : plan) {
					bw.write(strDate);
					bw.newLine();
					bw.write(strPlan);
					bw.newLine();
				}
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("파일을 작성하는데 오류가 발생했습니다.");
		}
	}
}
