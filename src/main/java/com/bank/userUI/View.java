package com.bank.userUI;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.Utility;
import com.bank.dao.BankDAO;
import com.bank.entity.Account;
import com.bank.entity.Transaction;

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
		Utility.log().info("| 5. See History Page                         |");
		Utility.log().info("| 6. Log out                                  |");
		Utility.log().info("+---------------------------------------------+");
	}
	
	public void historyPageText() {
		Utility.log().info("+----------------------+");
		Utility.log().info("|     History Menu     |");
		Utility.log().info("+----------------------+");
		Utility.log().info("| 1. Back to Main Menu |");
		Utility.log().info("+----------------------+");
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
	
	public void showHistoryText() {
		Utility.log().info("+------------------------------------------------------------------------------------------------------------------------------------+");
		Utility.log().info("| Welcome: "+printClientName());
		Utility.log().info("+------------------------------------------------------------------------------------------------------------------------------------+");
		Utility.log().info("|                                                              History Page                                                          |");
		Utility.log().info("+-----------------------------------------------------------------------+----------+---------------------+---------------------------+");
		Utility.log().info("|       Sender Account Number       |      Recipient Account Number     | Currency |         Date        |   Value (Balance After)   |");
		Utility.log().info("+-----------------------------------+-----------------------------------+----------+---------------------+---------------------------+");
		printTransactionsText();
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
	
	private void printTransactionsText() {
		try {
			for (Transaction a : bankDAO.getClientTransactions()) {
				Utility.log()
						.info("| " + a.displayAccountNo(a.getSenderAccountNo()) + "  | "
								+ a.displayAccountNo(a.getRecipientAccountNo()) + "  |   " + a.getCurrency() + "    | "
								+ a.getDateOfTransaction() + " | " + a.getAmountOfMoney() + "  ( " + a.getBalanceAfter()
								+ " )");
				Utility.log().info("+-----------------------------------|-----------------------------------|----------|"
						+ "---------------------+---------------------------+");
			}
		} catch (NoSuchElementException e) {
			Utility.log().warn("Error when loading transactions list");
		}
	}
	
	public String depositOrWithdraw() {
		Utility.log().info("Enter name of the account for money deposit/withdraw");
		return Utility.userInput().nextLine();
	}
	
	public String senderAccount() {
		Utility.log().info("Enter name of the 'SENDER' account");
		return Utility.userInput().nextLine();
	}
	
	public String recipientAccount() {
		Utility.log().info("Enter name of the 'RECIPIENT' account");
		return Utility.userInput().nextLine();
	}
	
	public double inputAmount() {
		try {
			Utility.log().info("Enter the amount of money for operation");
			return Utility.userInput().nextDouble();
		} catch (InputMismatchException e) {
			Utility.log().info("Error - Wrong input. Enter a valid amount of money");
		}

		return 0;
	}
	
	public String whatCurrency() {

		String currency = "";

		do {
			Utility.log().info("Enter currency for the new account: PLN , EUR , USD");
			currency = Utility.userInput().nextLine().toUpperCase();
		} while (!currency.equals("PLN") && !currency.equals("EUR") && !currency.equals("USD"));

		return currency;
	}
	
	public String inputFirstName() {
		String firstname;
		do {
			Utility.log().info(
					"Enter firstname (Should start with uppercase letter followed by lowercase letters. Only letters are allowed, minimum length is 2)");
			firstname = Utility.userInput().nextLine();
		} while (!Utility.validateRegex(firstname, Utility.getValidNameRegex()));

		return firstname;
	}

	public String inputLastName() {
		String lastname;
		do {
			Utility.log().info(
					"Enter lastname (Should start with uppercase letter followed by lowercase letters. Only letters are allowed, minimum length is 2)");
			lastname = Utility.userInput().nextLine();
		} while (!Utility.validateRegex(lastname, Utility.getValidNameRegex()));

		return lastname;
	}

	public String inputEmail() {
		String email;
		do {
			Utility.log().info("Enter email (Must be valid email pattern)");
			email = Utility.userInput().nextLine();
		} while (!Utility.validateRegex(email, Utility.getValidEmailAddressRegex()));

		return email;
	}
	
	public String inputUsername() {
		String username;
		do {
			Utility.log().info("Enter username (Only letters, digits and underscores allowed. Length between 4-15)");
			username = Utility.userInput().nextLine();
		} while (!Utility.validateRegex(username, Utility.getValidUsernameRegex()));

		return username;
	}
	
	public String inputPassword() {
		String password;
		do {
			Utility.log().info("Enter password (No whitespace characters allowed. Length between 4-25)");
			password = Utility.userInput().nextLine();
		} while (!Utility.validateRegex(password, Utility.getValidPasswordRegex()));

		return password;
	}

}
