package nanjeong.calendar;

import java.util.Scanner;

public class Prompt {
	
	public void runPrompt() {
		Scanner scan = new Scanner(System.in);
		Calendar cal = new Calendar();
		
		while (true) {
			System.out.println("년도를 입력하세요. ");
			System.out.print("YEAR> ");
			int year = scan.nextInt();
			if (year == -1) {
				break;
			}
			
			System.out.println("월을 입력하세요.");
			System.out.print("MONTH> ");
			int month = scan.nextInt();
			if (month == -1) {
				break;
			} else if (month < 1 || month > 12) {
				System.out.printf("%d월은 존재하지 않습니다.", month);
				System.out.println("\n");
			} else {
				cal.printCalendar(year, month);
				System.out.println("\n");
			}
		}

		System.out.println("\n종료합니다.");
		scan.close();
	}
	
	public static void main(String[] args) {
		Prompt p = new Prompt();
		p.runPrompt();
		
	}
}
