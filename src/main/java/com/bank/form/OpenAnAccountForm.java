package com.bank.form;

import org.springframework.stereotype.Component;

@Component
public class OpenAnAccountForm {
	
	private String firstname;
	private String lastname;
	private String email;
	private char[] username;
	private char[] password;
	
	public OpenAnAccountForm() {
		
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char[] getUsername() {
		return username;
	}

	public void setUsername(char[] username) {
		this.username = username;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

}
