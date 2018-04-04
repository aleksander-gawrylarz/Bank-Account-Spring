package com.bank.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Client {
	
	private String firstname;
	private String lastname;
	private String email;
	private char[] username;
	private char[] password;
	
	private List<Account> accountsList = new ArrayList<>();
	
	private List<Transaction> transcationList = new ArrayList<>();
	
	public Client() {
		
	}
	
	public Client(String firstname, String lastname, String email, char[] username, char[] password) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.username = username;
		this.password = password;
		
		accountsList.add(new AccountImpl());
		accountsList.get(0).setCurrency("PLN");
	}

	public boolean validateUserName(char[] toValidate) {
		
		if (Arrays.equals(toValidate, username))
			return true;
		else
			return false;
	}
	
	public boolean validatePassword(char[] toValidate) {
		
		if (Arrays.equals(toValidate, password))
			return true;
		else
			return false;
	}

	public String getFirstName() {
		return firstname;
	}

	public String getLastName() {
		return lastname;
	}

	public String getEmail() {
		return email;
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
	
	public List<Account> getAccountsList() {
		return accountsList;
	}

	public List<Transaction> getTranscationList() {
		return transcationList;
	}

	@Override
	public String toString() {
		return "Client [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + "]";
	}

}
