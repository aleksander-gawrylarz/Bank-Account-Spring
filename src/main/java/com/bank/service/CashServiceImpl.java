package com.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.Utility;
import com.bank.dao.BankDAO;
import com.bank.entity.Account;
import com.bank.userUI.View;

@Service
public class CashServiceImpl implements CashService {
	
	@Autowired
	private BankDAO bankDAO;

	@Autowired
	private View view;

	@Override
	public void deposit(List<String> input) {

		if (input.size() == 2) {

			for (Account a : bankDAO.getClientAccounts())
				if (a.getAccountName().equals(input.get(0))) {
					try {
					if (!a.deposit(Double.parseDouble(input.get(1))))
						Utility.log().info("Error - Cannot deposit negative amount");
					} catch (NumberFormatException e) {
					}
				}

			if (!bankDAO.getClientAccounts().stream()
					.anyMatch(a -> a.getAccountName().equals(input.get(0))))
				Utility.log().info("Error - Wrong account name");
		}

		view.accountListText();
		view.menuText();
	}

	@Override
	public void withdraw(List<String> input) {

		if (input.size() == 2) {

			for (Account a : bankDAO.getClientAccounts())
				if (a.getAccountName().equals(input.get(0))) {
					try {
					if (!a.withdraw(Double.parseDouble(input.get(1))))
						Utility.log().info("Error - Insufficient funds or negative value passed as amount");
					} catch (NumberFormatException e) {
					}
				}

			if (!bankDAO.getClientAccounts().stream()
					.anyMatch(a -> a.getAccountName().equals(input.get(0))))
				Utility.log().info("Error - Wrong account name");
		}

		view.accountListText();
		view.menuText();
	}
}
