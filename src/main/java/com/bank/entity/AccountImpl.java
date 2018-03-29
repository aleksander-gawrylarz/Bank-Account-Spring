package com.bank.entity;

import org.springframework.stereotype.Component;

@Component
public class AccountImpl implements Account {

	private String prefix = "102140310210014140";
	private static int suffix = 1105_0001;
	private static int accountNameSuffix = 0;

	private String accountNo;
	private String accountName;
	private long balance;
	
	private String currency = "";
	
	public AccountImpl() {
		balance = 0;
		accountName = "Account "+Integer.toString(accountNameSuffix);
		accountNo = prefix + Integer.toString(suffix);
		suffix += 3;
		accountNameSuffix++;
	}
	
	@Override
	public double checkBalance() {
		return balance / 100.0;
	}

	@Override
	public boolean deposit(double amount) {
		if (amount >= 0) {
			balance += amount * 100;
			return true;
		}
		return false;
	}

	@Override
	public boolean withdraw(double amount) {
		if (amount >= 0 && balance - amount * 100 >= 0) {
			balance -= amount * 100;
			return true;
		}
		return false;
	}
	
	@Override
	public String displayAccountNo() {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < accountNo.length(); i++) {
			sb.append(accountNo.charAt(i));
			if (i == 1 || i == 5 || i == 9 || i == 13 || i == 17 || i == 21)
				sb.append(" ");
		}
		return sb.toString();
	}

	@Override
	public String getAccountName() {
		return accountName;
	}

	@Override
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@Override
	public long getBalance() {
		return balance;
	}

	@Override
	public String getAccountNo() {
		return accountNo;
	}

	@Override
	public double withdrawAll() {
		double AllAmount = balance / 100.0;
		balance = 0;
		return AllAmount;
	}

	@Override
	public String getCurrency() {
		return currency;
	}

	@Override
	public boolean setCurrency(String newCurrency) {
		if (currency.isEmpty()) {
			currency = newCurrency;
			return true;
		}
		return false;
	}
		
}
