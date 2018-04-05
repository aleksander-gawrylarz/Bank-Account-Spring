package com.bank.service;

import java.math.BigDecimal;

public interface TransferService {

	public void transfer(String senderAccountName, String recipientAccountName, BigDecimal amount);
	
	public void deposit(String accountName, BigDecimal amount);
	
	public void withdraw(String accountName, BigDecimal amount);

}
