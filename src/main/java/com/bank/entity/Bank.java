package com.bank.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bank.form.LoginForm;

@Component
public class Bank {

	private List<Client> clientList;
	
	public Bank() {
		clientList = new ArrayList<>();
	}

	public List<Client> getClientList() {
		return clientList;
	}
	
	public Client getClient(LoginForm loginForm) {

		return clientList.stream().filter(c -> c.validateUserName(loginForm.getUsername())).findFirst().get();
	}

	@Override
	public String toString() {
		return "Bank [clientList=" + clientList + "]";
	}
	
}
