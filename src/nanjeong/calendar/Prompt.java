package nanjeong.calendar;

import java.util.Scanner;

public class Prompt {

	public void runPrompt() {
		Scanner scan = new Scanner(System.in);
		String cmd;
		Calendar cal = new Calendar();
		cal.help();

		while (true) {
			System.out.println("\n명령 (1, 2, 3, 4, h, q)");
			System.out.print("> ");
			cmd = scan.next();
			
			if (cmd.equals("q"))
				break;

			switch (cmd) {
			case "1":
				cal.registPlan(scan);
				break;
			case "2":
				cal.searchPlan(scan);
				break;
			case "3":
				cal.cancelPlan(scan);
				break;
			case "4":
				cal.printCalendar(scan);
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
		Prompt p = new Prompt();
		p.runPrompt();
	}
}
