package com.bank.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.BankAccountApplication;
import com.bank.dao.BankDAO;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=BankAccountApplication.class)
public class BankTest {
	
	@Autowired
	private BankDAO bankDAO;

	@Before
	public void setUp() throws Exception {
		
		bankDAO.getClients().add(new Client("John", "Doe", "johnD@gmail.com", new char[] {'j','o','h','n','7','5'}, 
				new char[] {'p','a','s','s','7','5'}));
		
		bankDAO.getLoginForm().setUsername(new char[] {'j','o','h','n','7','5'});
	}
	
	@Test
	public void whenGivenUsernameExistBankShouldReturnClientWithThatUsername() {
		
		Client expectedClient = bankDAO.getClients().get(0);
		
		assertEquals(expectedClient, bankDAO.getClient());
		
	}
}
