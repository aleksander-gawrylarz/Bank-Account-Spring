package com.bank.entity;

import java.math.BigDecimal;

public interface Account {

	public BigDecimal checkBalance();
	
	public void setAccountName(String accountName);
	
	public boolean deposit(BigDecimal amount);
	
	public boolean withdraw(BigDecimal amount);
	
	public String displayAccountNo();
	
	public String getAccountNo();
	
	public String getAccountName();
	
	public BigDecimal getBalance();
	
	public BigDecimal withdrawAll();
	
	public String getCurrency();
	
	public boolean setCurrency(String currency);
	
}
