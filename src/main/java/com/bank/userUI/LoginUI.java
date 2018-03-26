package com.bank.userUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.Utility;
import com.bank.entity.Bank;
import com.bank.service.OpenAnAccountService;
import com.bank.service.SignInService;

@Component
public class LoginUI {
	
	public static boolean proceed = true;
	
	@Autowired
	private SignInService signInService;
	
	@Autowired
	private OpenAnAccountService openAnAccountService;
	
	@Autowired
	private Bank bank;
	

	public void loginPage() {
		
		loginPageText();

		while (proceed) {

			switch (Utility.userInput().nextLine()) {

			case ("1"):
				signIn();
				break;
			case ("2"):
				openAnAccount();
				break;
			case ("3"):
				proceed = false;
				break;
			default:
				Utility.log().info("no such option");
				loginPageText();
			}
		}
	}
	
	private void openAnAccount() {
		
		String tempUsername;
		String tempPassword;

		do {
			Utility.log().info("Enter firstname (Should start with uppercase letter, only letters are allowed, minimum length is 2)");
			openAnAccountService.enterFirstname(Utility.userInput().nextLine());
		} while (!Utility.validateRegex(openAnAccountService.getOpenAnAccountForm().getFirstname(), Utility.getValidNameRegex()));
		
		do {
			Utility.log().info("Enter lastname (Should start with uppercase letter, only letters are allowed, minimum length is 2)");
			openAnAccountService.enterLastname(Utility.userInput().nextLine());
		} while (!Utility.validateRegex(openAnAccountService.getOpenAnAccountForm().getLastname(), Utility.getValidNameRegex()));
		
		do {
			Utility.log().info("Enter email (Must be valid email pattern)");
			openAnAccountService.enterEmail(Utility.userInput().nextLine());
		} while (!Utility.validateRegex(openAnAccountService.getOpenAnAccountForm().getEmail(), Utility.getValidEmailAddressRegex()));
			
		do {
			Utility.log().info("Enter username (Only letters, digits and underscores allowed. Length between 4-15)");
			tempUsername = Utility.userInput().nextLine();
			openAnAccountService.enterUsername(tempUsername.toCharArray());
		} while (!Utility.validateRegex(tempUsername, Utility.getValidUsernameRegex()));
		
		do {
			Utility.log().info("Enter password (No whitespace characters allowed. Length between 4-25)");
			tempPassword = Utility.userInput().nextLine();
			openAnAccountService.enterPassword(tempPassword.toCharArray());
		} while (!Utility.validateRegex(tempPassword, Utility.getValidPasswordRegex()));
		
		openAnAccountService.addNewClientToBank(openAnAccountService.getOpenAnAccountForm(), bank);

		loginPageText();
	}
	
	private void signIn() {
		Utility.log().info("Enter username");
		signInService.enterUsername(Utility.userInput().nextLine());
		Utility.log().info("Enter password");
		signInService.enterPassword(Utility.userInput().nextLine());
		
		signInService.signIn();
	}
	
	private void loginPageText() {
		Utility.log().info("+----------------------+");
		Utility.log().info("| Welcome to myBank.pl |");
		Utility.log().info("+----------------------+");
		Utility.log().info("| 1. Sign In           |");
		Utility.log().info("| 2. Open an Account   |");
		Utility.log().info("| 3. Quit              |");
		Utility.log().info("+----------------------+");
	}
	
}