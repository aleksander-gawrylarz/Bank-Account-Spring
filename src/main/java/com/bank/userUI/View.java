package com.bank.userUI;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.Utility;
import com.bank.dao.BankDAO;
import com.bank.entity.Account;

@Component
public class View {
	
	@Autowired
	private BankDAO bankDAO;
	
	public void loginPageText() {
		Utility.log().info("+----------------------+");
		Utility.log().info("| Welcome to myBank.pl |");
		Utility.log().info("+----------------------+");
		Utility.log().info("| 1. Sign In           |");
		Utility.log().info("| 2. Open an Account   |");
		Utility.log().info("| 3. Quit              |");
		Utility.log().info("+----------------------+");
	}
	
	public void menuText() {
		Utility.log().info("+---------------------------------------------+");
		Utility.log().info("|                  Main Menu                  |");
		Utility.log().info("+---------------------------------------------+");
		Utility.log().info("| 1. Deposit                                  |");
		Utility.log().info("| 2. Withdraw                                 |");
		Utility.log().info("| 3. Transfer                                 |");
		Utility.log().info("| 4. Open new account                         |");
		Utility.log().info("| 5. Log out                                  |");
		Utility.log().info("| Wait for next commit to see more actions :) |");
		Utility.log().info("+---------------------------------------------+");
	}
	
	public void accountListText() {
		Utility.log().info("+----------------------------------------------------------------------------------------+");
		Utility.log().info("| Welcome: "+printClientName());
		Utility.log().info("+----------------------------------------------------------------------------------------+");
		Utility.log().info("|                                    Bank Main Page                                      |");
		Utility.log().info("+--------------------------------------------------------+----------+--------------------+");
		Utility.log().info("| List of your current Accounts     |    Account Name    | Currency |      Balance       |");
		Utility.log().info("+-----------------------------------+--------------------+----------+--------------------+");
		printAccountsText();
	}
	
	private String printClientName() {

		String clientName = "";

		try {
			clientName = bankDAO.getClientFirstname() +" "+ bankDAO.getClientLastname();
		} catch (NoSuchElementException e) {
			Utility.log().warn("Error when loading client name information");
		}

		return clientName;
	}

	private void printAccountsText() {
		try {
			for (Account a : bankDAO.getClientAccounts()) {
				Utility.log().info("| " + a.displayAccountNo() + "  |     " + a.getAccountName() + "      |   "
						+ a.getCurrency() + "    | " + a.checkBalance());
				Utility.log().info(
						"+-----------------------------------|--------------------|----------|--------------------+");
			}
		} catch (NoSuchElementException e) {
			Utility.log().warn("Error when loading accounts list");
		}
	}
}
