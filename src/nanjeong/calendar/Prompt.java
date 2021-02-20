package nanjeong.calendar;

import java.util.Scanner;

public class Prompt {

	public void runPrompt() {
		Scanner scan = new Scanner(System.in);
		String cmd;
		PlanItem planItem = new PlanItem();
		planItem.initPlanMap();
		planItem.help();

		while (true) {
			System.out.println("\n명령 (1, 2, 3, 4, h, q)");
			System.out.print("> ");
			cmd = scan.next();
			
			if (cmd.equals("q"))
				break;

			switch (cmd) {
			case "1":
				planItem.registPlan(scan);
				break;
			case "2":
				planItem.searchPlan(scan);
				break;
			case "3":
				planItem.cancelPlan(scan);
				break;
			case "4":
				planItem.printCalendar(scan);
				break;
			case "h":
				planItem.help();
				break;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
		
		planItem.updateFile();
		System.out.println("\n종료합니다.");
		scan.close();
	}

	public static void main(String[] args) {
		Prompt p = new Prompt();
		p.runPrompt();
	}
}
