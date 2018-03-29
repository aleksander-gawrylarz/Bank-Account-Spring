package com.bank.entity;

public interface Account {

	public double checkBalance();
	
	public void setAccountName(String accountName);
	
	public boolean deposit(double amount);
	
	public boolean withdraw(double amount);
	
	public String displayAccountNo();
	
	public String getAccountNo();
	
	public String getAccountName();
	
	public long getBalance();
	
	public double withdrawAll();
	
	public String getCurrency();
	
	public boolean setCurrency(String currency);
	
}
