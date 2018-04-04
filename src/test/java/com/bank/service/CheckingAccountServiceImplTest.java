package com.bank.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.BankAccountApplication;
import com.bank.dao.BankDAO;
import com.bank.entity.Client;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=BankAccountApplication.class)
public class CheckingAccountServiceImplTest {
	
	@Autowired
	private CheckingAccountService checkingAccountService;
	
	@Autowired
	private BankDAO bankDAO;
	
	@Before
	public void setUp() throws Exception {
		
		bankDAO.getClients().add(new Client("John", "Doe", "johnD@gmail.com", new char[] {'j','o','h','n','7','5'}, 
				new char[] {'p','a','s','s','7','5'}));
		
		bankDAO.getLoginForm().setUsername(new char[] {'j','o','h','n','7','5'});
		bankDAO.getLoginForm().setPassword(new char[] {'p','a','s','s','7','5'});
		
		bankDAO.getClientAccounts().clear();
	}

	
	@Test
	public void whenCreatingNewAccountThenSizeOfAccountsListIsIncreasedByOne() {
		
		checkingAccountService.createNewAccount("PLN");
		assertEquals(1, bankDAO.getClientAccounts().size());
		
		checkingAccountService.createNewAccount("EUR");
		assertEquals(2, bankDAO.getClientAccounts().size());
	}
	
	@Test
	public void stringPassedAsArgumentToCreateNewAccountMethodSetsTheCurrencyOfNewAccount() {
		
		checkingAccountService.createNewAccount("USD");
		assertTrue(bankDAO.getClientAccounts().get(0).getCurrency().equals("USD"));
	}

}
