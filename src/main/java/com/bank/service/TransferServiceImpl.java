package com.bank.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.Utility;
import com.bank.dao.BankDAO;
import com.bank.entity.Account;
import com.bank.userUI.View;

@Service
public class TransferServiceImpl implements TransferService {
	
	@Autowired
	private BankDAO bankDAO;

	@Autowired
	private View view;
	
	@Override
	public void transfer(List<String> input) {

		if (input.size() == 3) {

			boolean success = true;

			if (bankDAO.getClientAccounts().stream()
					.anyMatch(a -> a.getAccountName().equals(input.get(0)))
					&& bankDAO.getClientAccounts().stream()
							.anyMatch(a -> a.getAccountName().equals(input.get(1)))) {

				if (checkIfTwoAccountsHaveTheSameCurrency(input.get(0), input.get(1))) {

					for (Account a : bankDAO.getClientAccounts()) {
						if (a.getAccountName().equals(input.get(0)))
							try {
							if (!a.withdraw(Double.parseDouble(input.get(2)))) {
								Utility.log().info("Error - Insufficient funds or negative value passed as amount");
								success = false;
								}
							} catch (NumberFormatException e) {
							}
					}

					if (success) {
						for (Account a : bankDAO.getClientAccounts()) {
							try {
							if (a.getAccountName().equals(input.get(1)))
								a.deposit(Double.parseDouble(input.get(2)));
							} catch (NumberFormatException e) {
							}
						}
					}

				} else
					Utility.log().info("Error - Accounts have different currency");
			}

			if (!bankDAO.getClientAccounts().stream()
					.anyMatch(a -> a.getAccountName().equals(input.get(0)))
					|| !bankDAO.getClientAccounts().stream()
							.anyMatch(a -> a.getAccountName().equals(input.get(1))))
				Utility.log().info("Error - At least one account name is wrong");
		}

		view.accountListText();
		view.menuText();
	}
	
	private boolean checkIfTwoAccountsHaveTheSameCurrency(String accountOne, String accountTwo) {

		List<String> accountsToCheck = new ArrayList<>();

		for (Account a : bankDAO.getClientAccounts())
			if (a.getAccountName().equals(accountOne) || a.getAccountName().equals(accountTwo))
				accountsToCheck.add(a.getCurrency());

		if (accountsToCheck.get(0).equals(accountsToCheck.get(1)))
			return true;

		return false;
	}
}
