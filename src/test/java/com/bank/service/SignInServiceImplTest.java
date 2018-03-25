package com.bank.service;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.BankAccountApplication;
import com.bank.form.LoginForm;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=BankAccountApplication.class)
public class SignInServiceImplTest {

	@Autowired
	private LoginForm loginForm;
	
	@Autowired
	private SignInService signInService;
	
	@Test
	public void usernameFromSignInShouldMatchUsernameFromLoginForm() {
		
		String username = "tomasz";
		char[] usernameCharArray = {'t','o','m','a','s','z'};
		
		char[] notValid = {'t','o','m'};
		
		signInService.enterUsername(username);
		
		assertArrayEquals(loginForm.getUsername(), usernameCharArray);
		assertFalse(Arrays.equals(loginForm.getUsername(), notValid));
	}

	@Test
	public void passwordFromSignInShouldMatchPasswordFromLoginForm() {
		
		String password = "motyl_94";
		char[] passwordCharArray = {'m','o','t','y','l','_','9','4'};
		
		char[] notValid = {'m','o','t','1'};
		
		signInService.enterPassword(password);
		
		assertArrayEquals(loginForm.getPassword(), passwordCharArray);
		assertFalse(Arrays.equals(loginForm.getPassword(), notValid));
	}

}
