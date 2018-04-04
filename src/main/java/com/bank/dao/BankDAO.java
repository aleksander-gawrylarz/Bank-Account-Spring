package com.bank.dao;

import java.util.List;

import com.bank.entity.Account;
import com.bank.entity.Client;
import com.bank.entity.Transaction;
import com.bank.form.LoginForm;

public interface BankDAO {
	
	public List<Account> getClientAccounts();
	
	public List<Transaction> getClientTransactions();
	
	public List<Client> getClients();
	
	public String getClientFirstname();
	
	public String getClientLastname();
	
	public LoginForm getLoginForm();
	
	public double getCurrencyValue(String currencyName);
	
	public Account getAccountByName(String accountName);

}
