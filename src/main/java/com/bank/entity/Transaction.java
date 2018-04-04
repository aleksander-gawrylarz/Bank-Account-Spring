package com.bank.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Component;

@Component
public class Transaction {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String senderAccountNo;
	private String recipientAccountNo;
	private double amountOfMoney;
	private double balanceAfter;
	private String currency;
	private String dateOfTransaction;

	public Transaction() {

	}

	public Transaction(String senderAccountNo, String recipientAccountNo, 
			double amountOfMoney, double balanceAfter, String currency) {

		this.senderAccountNo = senderAccountNo;
		this.recipientAccountNo = recipientAccountNo;
		this.amountOfMoney = amountOfMoney;
		this.balanceAfter = balanceAfter;
		this.currency = currency;
		this.dateOfTransaction = myTime(dateFormat);
	}

	public void setDateOfTransaction() {
		dateOfTransaction = myTime(dateFormat);
	}

	private String myTime(SimpleDateFormat dateFormat) {
		Calendar cal = Calendar.getInstance();
		dateFormat.setCalendar(cal);
		return dateFormat.format(cal.getTime());
	}

	public String getSenderAccountNo() {
		return senderAccountNo;
	}

	public String getRecipientAccountNo() {
		return recipientAccountNo;
	}

	public double getAmountOfMoney() {
		return amountOfMoney;
	}

	public double getBalanceAfter() {
		return balanceAfter;
	}

	public String getDateOfTransaction() {
		return dateOfTransaction;
	}

	public String getCurrency() {
		return currency;
	}

	public String displayAccountNo(String accountNo) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < accountNo.length(); i++) {
			sb.append(accountNo.charAt(i));
			if (i == 1 || i == 5 || i == 9 || i == 13 || i == 17 || i == 21)
				sb.append(" ");
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return "Transaction [senderAccountNo=" + displayAccountNo(senderAccountNo) + ", recipientAccountNo="
				+ displayAccountNo(recipientAccountNo) + ", amountOfMoney=" + amountOfMoney + ", balanceAfter="
				+ balanceAfter + ", currency=" + currency + ", dateOfTransaction=" + dateOfTransaction + "]";
	}
}
