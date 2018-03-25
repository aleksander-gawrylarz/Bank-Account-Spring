package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.Utility;
import com.bank.entity.Bank;
import com.bank.form.LoginForm;
import com.bank.userUI.BankMainUI;

@Service
public class SignInServiceImpl implements SignInService {
	
	@Autowired
	private LoginForm loginForm;
	
	@Autowired
	private Bank bank;
	
	@Autowired
	private BankMainUI bankMainUI;

	@Override
	public void enterUsername(String line) {
		
		char[] username = line.toCharArray();
		loginForm.setUsername(username);
	}

	@Override
	public void enterPassword(String line) {
		
		char[] password = line.toCharArray();
		loginForm.setPassword(password);
		
	}

	@Override
	public LoginForm getLoginForm() {
		return loginForm;
	}

	@Override
	public void signIn() {

		if (bank.getClientList().stream()
				.anyMatch(element -> element.validateUserName(getLoginForm().getUsername())
						&& element.validatePassword(getLoginForm().getPassword())))
			bankMainUI.bankMainPage();
		else {
			Utility.log().info("Invalid username or password");
			loginPageText();
		}
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
