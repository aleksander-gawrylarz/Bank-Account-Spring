package com.bank.service;

public interface TransferService {

	public void transfer(String senderAccountName, String recipientAccountName, double amount);
	
	public void deposit(String accountName, double amount);
	
	public void withdraw(String accountName, double amount);

}
