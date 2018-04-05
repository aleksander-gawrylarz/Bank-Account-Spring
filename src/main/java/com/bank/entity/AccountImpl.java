package com.bank.entity;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class AccountImpl implements Account {

	private String prefix = "102140310210014140";
	private static int suffix = 1105_0001;
	private static int accountNameSuffix = 0;

	private String accountNo;
	private String accountName;
	private BigDecimal balance;
	
	private String currency = "";
	
	public AccountImpl() {
		balance = BigDecimal.ZERO;
		accountName = "Account "+Integer.toString(accountNameSuffix);
		accountNo = prefix + Integer.toString(suffix);
		suffix += 3;
		accountNameSuffix++;
	}
	
	@Override
	public BigDecimal checkBalance() {
		return balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	@Override
	public boolean deposit(BigDecimal amount) {
		if (amount.compareTo(new BigDecimal("0")) == 1 || amount.compareTo(new BigDecimal("0")) == 0) {
			balance = balance.add(amount);
			return true;
		}
		return false;
	}

	@Override
	public boolean withdraw(BigDecimal amount) {
		BigDecimal afterWithdraw = balance.subtract(amount);

		if ((amount.compareTo(new BigDecimal("0")) == 1 || amount.compareTo(new BigDecimal("0")) == 0)
				&& (afterWithdraw.compareTo(new BigDecimal("0")) == 1
						|| afterWithdraw.compareTo(new BigDecimal("0")) == 0)) {
			balance = balance.subtract(amount);
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
	public BigDecimal getBalance() {
		return balance;
	}

	@Override
	public String getAccountNo() {
		return accountNo;
	}

	@Override
	public BigDecimal withdrawAll() {
		BigDecimal AllAmount = balance;
		balance = BigDecimal.ZERO;
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
