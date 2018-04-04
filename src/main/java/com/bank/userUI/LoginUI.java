package com.bank.userUI;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.Utility;
import com.bank.service.OpenBankAccountService;
import com.bank.service.SignInService;

@Component
public class LoginUI {

	public static boolean proceed = true;

	@Autowired
	private SignInService signInService;

	@Autowired
	private OpenBankAccountService openBankAccountService;

	@Autowired
	private View view;

	public void loginPage(Scanner scanner) {

		String firstname;
		String lastname;
		String email;
		String username;
		String password;

		view.loginPageText();

		while (proceed) {

			switch (scanner.nextLine()) {

			case ("1"):
				BankMainUI.proceed = true;
				signInService.signIn();
				break;
			case ("2"):
				firstname = view.inputFirstName();
				openBankAccountService.enterFirstname(firstname);

				lastname = view.inputLastName();
				openBankAccountService.enterLastname(lastname);

				email = view.inputEmail();
				openBankAccountService.enterEmail(email);

				username = view.inputUsername();
				openBankAccountService.enterUsername(username.toCharArray());

				password = view.inputPassword();
				openBankAccountService.enterPassword(password.toCharArray());

				openBankAccountService.addNewClientToBank(openBankAccountService.getOpenAnAccountForm());
				view.loginPageText();
				break;
			case ("3"):
				proceed = false;
				break;
			default:
				Utility.log().info("no such option");
				view.loginPageText();
			}
		}
	}
}
