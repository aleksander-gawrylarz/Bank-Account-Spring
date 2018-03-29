package com.bank.userUI;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.Utility;
import com.bank.service.CashService;
import com.bank.service.CheckingAccountService;
import com.bank.service.TransferService;

@Component
public class BankMainUI {
	
	public static boolean proceed = true;
	
	@Autowired
	private CashService cashService;
	
	@Autowired
	private TransferService transferService;
	
	@Autowired
	private CheckingAccountService checkingAccountService;
	
	@Autowired
	private LoginUI loginUI;
	
	@Autowired
	private View view;

	public void bankMainPage(Scanner scanner) {
		LoginUI.proceed = false;
		view.accountListText();
		view.menuText();
		
		while (proceed) {

			switch (scanner.nextLine()) {

			case ("1"):
				cashService.deposit(inputForDeposit(true));
				break;
			case ("2"):
				cashService.withdraw(inputForDeposit(false));
				break;
			case ("3"):
				transferService.transfer(inputForTransfer());
				break;		
			case ("4"):
				checkingAccountService.createNewAccount(whatCurrency());
				break;
			case ("5"):
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
	
	private String whatCurrency() {
		
		String currency = "";
		
		do {
		Utility.log().info("Enter currency for the new account: PLN , EUR , USD");
		currency = Utility.userInput().nextLine().toUpperCase();
		} while (!currency.equals("PLN") && !currency.equals("EUR") && !currency.equals("USD"));
		
		return currency;
	}
	
	private List<String> inputForDeposit(boolean deposit) {

		List<String> userInput = new ArrayList<>();
		userInput.add("Wrong-Account-Name");
		userInput.add("0");

		String accountName;
		double amount = 0;

		if (deposit)
			Utility.log().info("Enter name of the account for money deposit");
		else
			Utility.log().info("Enter name of the account for money withdraw");

		accountName = Utility.userInput().nextLine();
		userInput.set(0, accountName);

		try {
			if (deposit)
				Utility.log().info("Enter the amount of money to deposit");
			else
				Utility.log().info("Enter the amount of money to withdraw");
			
			amount = Utility.userInput().nextDouble();
			userInput.set(1, Double.toString(amount));
			
		} catch (InputMismatchException e) {
			Utility.log().info("Error - Wrong input. Enter a valid amount of money");
		}

		return userInput;
	}
	
	private List<String> inputForTransfer() {

		List<String> userInput = new ArrayList<>();
		userInput.add("Wrong-Sender-Account-Name");
		userInput.add("Wrong-Recipient-Account-Name");
		userInput.add("0");

		String senderAccountName;
		String recipientAccountName;
		double amount = 0;

		Utility.log().info("Enter name of the 'SENDER' account");
		senderAccountName = Utility.userInput().nextLine();
		userInput.set(0, senderAccountName);

		Utility.log().info("Enter name of the 'RECIPIENT' account");
		recipientAccountName = Utility.userInput().nextLine();
		userInput.set(1, recipientAccountName);

		try {
			Utility.log().info("Enter the amount of money to transfer");
			amount = Utility.userInput().nextDouble();
			userInput.set(2, Double.toString(amount));
		} catch (InputMismatchException e) {
			Utility.log().info("Error - Wrong input. Enter a valid amount of money");
		}

		return userInput;
	}

}
