package com.bank.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bank.entity.Account;
import com.bank.entity.Bank;
import com.bank.entity.Client;
import com.bank.entity.Transaction;
import com.bank.form.LoginForm;

@Repository
public class BankDAOImpl implements BankDAO {
	
	@Autowired
	private Bank bank;
	
	@Autowired
	private CurrencyDAO currencyDAO;
	
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
	public double getCurrencyValue(String currencyName) {
		return currencyDAO.getCurrencyValue(currencyName);
	}

	@Override
	public Account getAccountByName(String accountName) {
		return getClientAccounts().stream().filter(a -> a.getAccountName().equals(accountName)).findFirst().get();
	}

	@Override
	public List<Transaction> getClientTransactions() {
		return bank.getClient(loginForm).getTranscationList();
	}
}
