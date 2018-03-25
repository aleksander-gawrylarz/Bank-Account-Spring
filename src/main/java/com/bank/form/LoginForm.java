package com.bank.form;

import org.springframework.stereotype.Component;

@Component
public class LoginForm {
	
	private char[] username;
	private char[] password;
	
	public LoginForm() {
		
	}
	
	public char[] getUsername() {
		return username;
	}
	public void setUsername(char[] login) {
		this.username = login;
	}
	public char[] getPassword() {
		return password;
	}
	public void setPassword(char[] password) {
		this.password = password;
	}
	
}
