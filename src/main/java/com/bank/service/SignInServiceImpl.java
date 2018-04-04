package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.Utility;
import com.bank.dao.BankDAO;
import com.bank.form.LoginForm;
import com.bank.userUI.BankMainUI;
import com.bank.userUI.View;

@Service
public class SignInServiceImpl implements SignInService {

	@Autowired
	private LoginForm loginForm;

	@Autowired
	private BankDAO bankDAO;

	@Autowired
	private BankMainUI bankMainUI;

	@Autowired
	private View view;

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
	public boolean signIn() {

		Utility.log().info("Enter username");
		enterUsername(Utility.userInput().nextLine());
		Utility.log().info("Enter password");
		enterPassword(Utility.userInput().nextLine());

		if (bankDAO.getClients().stream().anyMatch(element -> element.validateUserName(getLoginForm().getUsername())
				&& element.validatePassword(getLoginForm().getPassword()))) {
			bankMainUI.bankMainPage(Utility.userInput());
			return true;
		} else {
			Utility.log().info("Invalid username or password");
			view.loginPageText();
		}
		return false;
	}
}
