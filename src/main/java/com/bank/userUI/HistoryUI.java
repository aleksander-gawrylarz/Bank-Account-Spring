package com.bank.userUI;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.Utility;

@Component
public class HistoryUI {

	public static boolean proceed = true;

	@Autowired
	private BankMainUI bankMainUI;

	@Autowired
	private View view;
	
	public void historyPage(Scanner scanner) {

		BankMainUI.proceed = false;

		view.showHistoryText();
		view.historyPageText();

		while (proceed) {

			switch (scanner.nextLine()) {

			case ("1"):
				proceed = false;
				BankMainUI.proceed = true;
				bankMainUI.bankMainPage(Utility.userInput());
				break;
			default:
				Utility.log().info("no such option");

			}
		}
	}
}
