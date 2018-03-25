package com.bank.service;

import com.bank.form.LoginForm;

public interface SignInService {
	
	public void enterUsername(String username);
	
	public void enterPassword(String password);
	
	public void signIn();
	
	public LoginForm getLoginForm();

}
