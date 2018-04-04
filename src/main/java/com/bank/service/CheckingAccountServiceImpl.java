package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.dao.BankDAO;
import com.bank.entity.AccountImpl;
import com.bank.userUI.View;

@Service
public class CheckingAccountServiceImpl implements CheckingAccountService {
	
	@Autowired
	private BankDAO bankDAO;
	
	@Autowired
	private View view;

	@Override
	public void createNewAccount(String currency) {
		bankDAO.getClientAccounts().add(new AccountImpl());
		bankDAO.getClientAccounts().get(bankDAO.getClientAccounts().size()-1)
				.setCurrency(currency);
		view.accountListText();
		view.menuText();
	}

}
