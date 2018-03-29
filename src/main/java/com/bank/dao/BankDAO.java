package com.bank.dao;

import java.util.List;

import com.bank.entity.Account;
import com.bank.entity.Client;
import com.bank.form.LoginForm;

public interface BankDAO {
	
	public List<Account> getClientAccounts();
	
	public List<Client> getClients();
	
	public String getClientFirstname();
	
	public String getClientLastname();
	
	public LoginForm getLoginForm();
	
	public Client getClient();

}
