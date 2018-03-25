package com.bank.userUI;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.Utility;
import com.bank.entity.Account;
import com.bank.entity.AccountImpl;
import com.bank.entity.Bank;
import com.bank.form.LoginForm;

@Component
public class BankMainUI {
	
	public static boolean proceed = true;
	
	@Autowired
	private Bank bank;
	
	@Autowired
	private LoginForm loginForm;

	public void bankMainPage() {
		LoginUI.proceed = false;
		accountListText();
		menuText();
		
		while (proceed) {

			switch (Utility.userInput().nextLine()) {

			case ("1"):
				openNewAccount();
				break;
			case ("2"):
				proceed = false;
				break;
			default:
				Utility.log().info("no such option");
				accountListText();
				menuText();
			}
		}
	}
	
	private void openNewAccount() {
		bank.getClient(loginForm).getAccountsList().add(new AccountImpl());
		accountListText();
		menuText();
	}
	
	private void accountListText() {
		Utility.log().info("+-----------------------------------------------------------------------------+");
		Utility.log().info("|                               Bank Main Page                                |");
		Utility.log().info("+--------------------------------------------------------+--------------------+");
		Utility.log().info("| List of your current Accounts     |    Account Name    |      Balance       |");
		Utility.log().info("+-----------------------------------+--------------------+--------------------+");
		printAccountsText();
	}
	
	private void printAccountsText() {
		try {
			for (Account a : bank.getClient(loginForm).getAccountsList()) {
				Utility.log().info("| " + a.displayAccountNo() + "  |     " + a.getAccountName() + "      | "
						+ a.checkBalance());
				Utility.log().info("+-----------------------------------|--------------------|--------------------+");

			}
		} catch (NoSuchElementException e) {
			Utility.log().warn("Error when loading accounts list");
		}
	}
	
	private void menuText() {
		Utility.log().info("+---------------------------------------------+");
		Utility.log().info("|                  Main Menu                  |");
		Utility.log().info("+---------------------------------------------+");
		Utility.log().info("| 1. Open new account                         |");
		Utility.log().info("| 2. Quit                                     |");
		Utility.log().info("| Wait for next commit to see more actions :) |");
		Utility.log().info("+---------------------------------------------+");
	}
	
}
