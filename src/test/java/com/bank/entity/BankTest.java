package com.bank.entity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import com.bank.form.LoginForm;

public class BankTest {
	
	private Bank bank;
	
	private LoginForm loginForm = mock(LoginForm.class);

	@Before
	public void setUp() throws Exception {
		
		bank = new Bank();
		
		bank.getClientList().add(new Client("John", "Doe", "johnD@gmail.com", new char[] {'j','o','h','n','7','5'}, 
				new char[] {'p','a','s','s','7','5'}));
		bank.getClientList().add(new Client("Marry", "Smith", "Marry@wp.pl", new char[] {'m','a','r','r'}, 
				new char[] {'1','p','2','3'}));
	}
	
	@Test
	public void whenGivenUsernameExistBankShouldReturnClientWithThatUsername() {
		
		Client expectedClient = bank.getClientList().get(0);
		when(loginForm.getUsername()).thenReturn(new char[] {'j','o','h','n','7','5'});
		
		assertEquals(expectedClient, bank.getClient(loginForm));
		
	}
	
	@Test (expected = NoSuchElementException.class)
	public void whenGivenUsernameDoesNotExistNoSuchElementExceptionIsThrown() {
		
		Client expectedClient = bank.getClientList().get(1);
		when(loginForm.getUsername()).thenReturn(new char[] {});
		
		assertEquals(expectedClient, bank.getClient(loginForm));
		
	}

}
