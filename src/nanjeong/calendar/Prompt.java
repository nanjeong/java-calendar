package nanjeong.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Prompt {
	
	public void runPrompt() {
		Scanner scan = new Scanner(System.in);
		Calendar cal = new Calendar();
		final String PROMPT = "> ";
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		String date;
		String inPlan;
		String cmd;
		
		while (true) {			
			System.out.println("\n명령 (1, 2, 3, 4, h, q)");
			System.out.print(PROMPT);
			cmd = scan.next();
			
			if (cmd.equals("q")) {
				break;
			}
			switch (cmd) {
				case "1":
					System.out.print("[일정 등록] ");
					date = cal.inputDate();
					if (cal.isRightDate(date)) {
						inPlan = cal.inputPlan();
					
						if (map.containsKey(date)) {
							map.get(date).add(inPlan);
						} else {
							ArrayList<String> plan = new ArrayList<String>();
							plan.add(inPlan);
							map.put(date, plan);
						}
						System.out.println("일정이 등록되었습니다.");
					} else {
						System.out.printf("%s는 존재하지 않는 날짜입니다.\n", date);
					}
					break;
				case "2":
					System.out.print("[일정 검색] ");
					date = cal.inputDate();
					if (map.get(date) == null) {
						System.out.println("일정이 없습니다.");
					} else {
						cal.searchPlan(map, date);
					}
					break;
				case "3":
					System.out.print("[일정 취소] ");
					date = cal.inputDate();
					
					if (map.get(date) == null) {
						System.out.println("일정이 없습니다.");
					} else {
						cal.searchPlan(map, date);
						System.out.println("\n취소하고 싶은 일정의 번호를 입력하세요.");
						System.out.print(PROMPT);
						int idx = scan.nextInt();
						int cnt = map.get(date).size();
						
						if (0 < idx && idx <= cnt) {
							map.get(date).remove(idx-1);
							if (map.get(date).size() == 0) {
								map.remove(date);
								System.out.println("일정이 없습니다.");
							} else {
								cal.searchPlan(map, date);
							}
						} else {
							System.out.println("잘못된 입력입니다.");
						}
					}
					break;
				case "4":
					try {
						System.out.print("YEAR> ");
						int year = scan.nextInt();
						
						if (year < 1) {
							System.out.printf("%d년은 존재하지 않습니다.", year);
							break;
						}
					
						System.out.print("MONTH> ");
						int month = scan.nextInt();
					
						if (month < 1 || month > 12) {
							System.out.printf("%d월은 존재하지 않습니다.", month);
							break;
						}
						cal.printCalendar(year, month, map);
					} catch (InputMismatchException e) {
						System.out.println("잘못된 입력입니다.");
						scan.nextLine();
					}
					break;
				case "h":
					cal.help();
					break;
				default:
					System.out.println("잘못된 입력입니다.");
			}
			
		}

		System.out.println("\n종료합니다.");
		scan.close();
	}
	
	public static void main(String[] args) {
		Calendar cal = new Calendar();
		cal.help();
		
		Prompt p = new Prompt();
		p.runPrompt();
		
	}
}
