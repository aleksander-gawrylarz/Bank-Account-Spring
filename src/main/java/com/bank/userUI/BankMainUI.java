package com.bank.userUI;

import java.math.BigDecimal;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.Utility;
import com.bank.service.CheckingAccountService;
import com.bank.service.TransferService;

@Component
public class BankMainUI {

	public static boolean proceed = true;

	@Autowired
	private TransferService transferService;

	@Autowired
	private CheckingAccountService checkingAccountService;

	@Autowired
	private LoginUI loginUI;
	
	@Autowired
	private HistoryUI historyUI;

	@Autowired
	private View view;

	public void bankMainPage(Scanner scanner) {
		
		String accountName;
		String recipientAccountName;
		BigDecimal amount;
		
		LoginUI.proceed = false;
		view.accountListText();
		view.menuText();

		while (proceed) {

			switch (scanner.nextLine()) {

			case ("1"):	
				accountName = view.depositOrWithdraw();
				amount = view.inputAmount();
				transferService.deposit(accountName, amount);
				break;
			case ("2"):
				accountName = view.depositOrWithdraw();
				amount = view.inputAmount();
				transferService.withdraw(accountName, amount);
				break;
			case ("3"):
				accountName = view.senderAccount();
				recipientAccountName = view.recipientAccount();
				amount = view.inputAmount();
				transferService.transfer(accountName, recipientAccountName, amount);
				break;
			case ("4"):
				checkingAccountService.createNewAccount(view.whatCurrency());
				break;
			case ("5"):
				HistoryUI.proceed = true;
				historyUI.historyPage(Utility.userInput());
				break;	
			case ("6"):
				proceed = false;
				LoginUI.proceed = true;
				loginUI.loginPage(Utility.userInput());
				break;
			default:
				Utility.log().info("no such option");
				view.accountListText();
				view.menuText();
			}
		}
	}
}
