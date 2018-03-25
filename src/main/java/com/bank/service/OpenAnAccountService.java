package com.bank.service;

import com.bank.entity.Bank;
import com.bank.form.OpenAnAccountForm;

public interface OpenAnAccountService {

	public void enterFirstname(String firstname);

	public void enterLastname(String lastname);

	public void enterEmail(String email);

	public void enterUsername(char[] username);

	public void enterPassword(char[] password);
	
	public OpenAnAccountForm getOpenAnAccountForm();

	public void addNewClientToBank(OpenAnAccountForm clientForm, Bank bank);
	
}
