package com.bank.service;

import java.math.BigDecimal;
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
	public void transfer(String senderAccountName, String recipientAccountName, BigDecimal amount) {

		boolean success = true;

		if (bankDAO.getClientAccounts().stream().anyMatch(a -> a.getAccountName().equals(senderAccountName)) && bankDAO
				.getClientAccounts().stream().anyMatch(a -> a.getAccountName().equals(recipientAccountName))) {

			if (!bankDAO.getAccountByName(senderAccountName).withdraw(amount)) {
				Utility.log().info("Error - Insufficient funds or negative value passed as amount");
				success = false;
			} else
				addTransferTransaction(senderAccountName, senderAccountName, amount.multiply(new BigDecimal("-1")));

			if (success) {
				if (!checkIfTwoAccountsHaveTheSameCurrency(senderAccountName, recipientAccountName))
					amount = setExchange(senderAccountName, recipientAccountName, amount);

				bankDAO.getAccountByName(recipientAccountName).deposit(amount);
				addTransferTransaction(senderAccountName, recipientAccountName, amount);
			}

		} else
			Utility.log().info("Error - At least one account name is wrong");

		view.accountListText();
		view.menuText();
	}

	@Override
	public void deposit(String accountName, BigDecimal amount) {
		try {
			if (!bankDAO.getAccountByName(accountName).deposit(amount))
				Utility.log().info("Error - Cannot deposit negative amount");
			else
				addTransaction(accountName, amount);
		} catch (NoSuchElementException e) {
			Utility.log().info("Error - Wrong account name or negative value passed as amount");
		}
		view.accountListText();
		view.menuText();
	}

	@Override
	public void withdraw(String accountName, BigDecimal amount) {
		try {
			if (!bankDAO.getAccountByName(accountName).withdraw(amount))
				Utility.log().info("Error - Insufficient funds or negative value passed as amount");
			else
				addTransaction(accountName, amount.multiply(new BigDecimal("-1")));
		} catch (NoSuchElementException e) {
			Utility.log().info("Error - Wrong account name or negative value passed as amount");
		}
		view.accountListText();
		view.menuText();
	}

	private void addTransaction(String accountName, BigDecimal amount) {

		String senderAccountNo;
		String recipientAccountNo;
		BigDecimal balanceAfter;
		String currency;

		senderAccountNo = bankDAO.getAccountByName(accountName).getAccountNo();

		recipientAccountNo = senderAccountNo;

		balanceAfter = bankDAO.getAccountByName(accountName).checkBalance();

		currency = bankDAO.getAccountByName(accountName).getCurrency();
		
		if (amount.compareTo(new BigDecimal("0")) != 0)
		bankDAO.getClientTransactions()
				.add(new Transaction(senderAccountNo, recipientAccountNo, amount, balanceAfter, currency));
	}

	private void addTransferTransaction(String senderAccountName, String recipientAccountName, BigDecimal amount) {

		String senderAccountNo;
		String recipientAccountNo;
		BigDecimal balanceAfter;
		String currency;

		senderAccountNo = bankDAO.getAccountByName(senderAccountName).getAccountNo();

		recipientAccountNo = bankDAO.getAccountByName(recipientAccountName).getAccountNo();

		if (amount.compareTo(BigDecimal.ZERO) == -1)
			balanceAfter = bankDAO.getAccountByName(senderAccountName).checkBalance();
		else
			balanceAfter = bankDAO.getAccountByName(recipientAccountName).checkBalance();

		currency = bankDAO.getAccountByName(recipientAccountName).getCurrency();

		if (amount.compareTo(new BigDecimal("0")) != 0)
		bankDAO.getClientTransactions()
				.add(new Transaction(senderAccountNo, recipientAccountNo, amount, balanceAfter, currency));
	}

	private boolean checkIfTwoAccountsHaveTheSameCurrency(String accountOne, String accountTwo) {

		if (bankDAO.getAccountByName(accountOne).getCurrency()
				.equals(bankDAO.getAccountByName(accountTwo).getCurrency()))
			return true;

		return false;
	}

	private BigDecimal setExchange(String accountOne, String accountTwo, BigDecimal toExchange) {

		BigDecimal currencyOne = bankDAO.getCurrencyValue(bankDAO.getAccountByName(accountOne).getCurrency());
		BigDecimal currencyTwo = bankDAO.getCurrencyValue(bankDAO.getAccountByName(accountTwo).getCurrency());

		BigDecimal exchange = (currencyOne.divide(currencyTwo, BigDecimal.ROUND_HALF_EVEN)).multiply(toExchange);

		return exchange;
	}
}
