package nanjeong.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
			System.out.println("\n명령 (1, 2, 3, h, q)");
			System.out.print(PROMPT);
			cmd = scan.next();
			
			if (cmd.equals("q")) {
				break;
			}
			switch (cmd) {
				case "1":
					System.out.print("[일정 등록] ");
					date = cal.inputDate();
					inPlan = cal.inputPlan();
					
					if (map.containsKey(date)) {
						map.get(date).add(inPlan);
					} else {
						ArrayList<String> plan = new ArrayList<String>();
						plan.add(inPlan);
						map.put(date, plan);
					}
					System.out.println("일정이 등록되었습니다.");
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
					System.out.println("년도를 입력하세요.");
					System.out.print("YEAR> ");
					int year = scan.nextInt();
					
					if (year < 1) {
						System.out.printf("%d년은 존재하지 않습니다.", year);
						break;
					}
					
					System.out.println("월을 입력하세요.");
					System.out.print("MONTH> ");
					int month = scan.nextInt();
					
					if (month < 1 || month > 12) {
						System.out.printf("%d월은 존재하지 않습니다.", month);
						break;
					}
					cal.printCalendar(year, month, map);
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
