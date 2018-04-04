package com.bank.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.Utility;
import com.bank.dao.BankDAO;
import com.bank.entity.Transaction;
import com.bank.userUI.View;

@Service
public class TransferServiceImpl implements TransferService {

	@Autowired
	private BankDAO bankDAO;

	@Autowired
	private View view;

	@Override
	public void transfer(String senderAccountName, String recipientAccountName, double amount) {

		boolean success = true;

		if (bankDAO.getClientAccounts().stream().anyMatch(a -> a.getAccountName().equals(senderAccountName)) && bankDAO
				.getClientAccounts().stream().anyMatch(a -> a.getAccountName().equals(recipientAccountName))) {

			if (!bankDAO.getAccountByName(senderAccountName).withdraw(amount)) {
				Utility.log().info("Error - Insufficient funds or negative value passed as amount");
				success = false;
			} else
				addTransferTransaction(senderAccountName, senderAccountName, (long) (-amount * 100));

			if (success) {
				if (!checkIfTwoAccountsHaveTheSameCurrency(senderAccountName, recipientAccountName))
					amount = setExchange(senderAccountName, recipientAccountName, amount);

				bankDAO.getAccountByName(recipientAccountName).deposit(amount);
				addTransferTransaction(senderAccountName, recipientAccountName, (long) (amount * 100));
			}

		} else
			Utility.log().info("Error - At least one account name is wrong");

		view.accountListText();
		view.menuText();
	}

	@Override
	public void deposit(String accountName, double amount) {
		try {
			if (!bankDAO.getAccountByName(accountName).deposit(amount))
				Utility.log().info("Error - Cannot deposit negative amount");
			else
				addTransaction(accountName, (long) (amount * 100));
		} catch (NoSuchElementException e) {
			Utility.log().info("Error - Wrong account name or negative value passed as amount");
		}
		view.accountListText();
		view.menuText();
	}

	@Override
	public void withdraw(String accountName, double amount) {
		try {
			if (!bankDAO.getAccountByName(accountName).withdraw(amount))
				Utility.log().info("Error - Insufficient funds or negative value passed as amount");
			else
				addTransaction(accountName, (long) (-amount * 100));
		} catch (NoSuchElementException e) {
			Utility.log().info("Error - Wrong account name or negative value passed as amount");
		}
		view.accountListText();
		view.menuText();
	}

	private void addTransaction(String accountName, long amount) {

		String senderAccountNo;
		String recipientAccountNo;
		double amountOfMoney;
		double balanceAfter;
		String currency;

		senderAccountNo = bankDAO.getAccountByName(accountName).getAccountNo();

		recipientAccountNo = senderAccountNo;

		amountOfMoney = amount / 100.0;

		balanceAfter = bankDAO.getAccountByName(accountName).checkBalance();

		currency = bankDAO.getAccountByName(accountName).getCurrency();

		bankDAO.getClientTransactions()
				.add(new Transaction(senderAccountNo, recipientAccountNo, amountOfMoney, balanceAfter, currency));
	}

	private void addTransferTransaction(String senderAccountName, String recipientAccountName, long amount) {

		String senderAccountNo;
		String recipientAccountNo;
		double amountOfMoney;
		double balanceAfter;
		String currency;

		senderAccountNo = bankDAO.getAccountByName(senderAccountName).getAccountNo();

		recipientAccountNo = bankDAO.getAccountByName(recipientAccountName).getAccountNo();

		amountOfMoney = amount / 100.0;

		if (amount < 0)
			balanceAfter = bankDAO.getAccountByName(senderAccountName).checkBalance();
		else
			balanceAfter = bankDAO.getAccountByName(recipientAccountName).checkBalance();

		currency = bankDAO.getAccountByName(recipientAccountName).getCurrency();

		bankDAO.getClientTransactions()
				.add(new Transaction(senderAccountNo, recipientAccountNo, amountOfMoney, balanceAfter, currency));
	}

	private boolean checkIfTwoAccountsHaveTheSameCurrency(String accountOne, String accountTwo) {

		if (bankDAO.getAccountByName(accountOne).getCurrency()
				.equals(bankDAO.getAccountByName(accountTwo).getCurrency()))
			return true;

		return false;
	}

	private double setExchange(String accountOne, String accountTwo, double toExchange) {

		double exchange;

		exchange = (bankDAO.getCurrencyValue(bankDAO.getAccountByName(accountOne).getCurrency())
				/ bankDAO.getCurrencyValue(bankDAO.getAccountByName(accountTwo).getCurrency())) * toExchange;

		return exchange;
	}
}
