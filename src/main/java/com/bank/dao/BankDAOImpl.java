package com.bank.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bank.entity.Account;
import com.bank.entity.Bank;
import com.bank.entity.Client;
import com.bank.form.LoginForm;

@Repository
public class BankDAOImpl implements BankDAO {
	
	@Autowired
	private Bank bank;
	
	@Autowired
	private LoginForm loginForm;

	@Override
	public List<Account> getClientAccounts() {
		
		return bank.getClient(loginForm).getAccountsList();
	}

	@Override
	public String getClientFirstname() {
		
		return bank.getClient(loginForm).getFirstName();
	}

	@Override
	public String getClientLastname() {
		
		return bank.getClient(loginForm).getLastName();
	}

	@Override
	public List<Client> getClients() {
		
		return bank.getClientList();
	}

	@Override
	public LoginForm getLoginForm() {
		
		return loginForm;
	}

	@Override
	public Client getClient() {
		
		return bank.getClient(loginForm);
	}
}
