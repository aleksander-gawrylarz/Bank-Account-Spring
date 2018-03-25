package com.bank.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.BankAccountApplication;
import com.bank.entity.Bank;
import com.bank.entity.Client;
import com.bank.form.OpenAnAccountForm;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=BankAccountApplication.class)
public class OpenAnAccountServiceImplTest {
	
	@Autowired
	private OpenAnAccountService openAnAccountService;

	@Autowired
	private OpenAnAccountForm openAnAccountForm;
	
	@Autowired
	private Bank bank;
	
	@Before
	public void setUp() throws Exception {

		openAnAccountForm.setFirstname("Artur");
		openAnAccountForm.setLastname("Bobas");
		openAnAccountForm.setEmail("artibob@wp.pl");
		openAnAccountForm.setUsername(new char[] { 'a', 'r', 't', 'i', '9', '2' });
		openAnAccountForm.setPassword(new char[] { 'm', 'o', 't', 'y', 'l', '6' });

	}
	
	@Test
	public void addingNewClientToEmptyBankShouldIncreaseClientSizeByOne() {
		
		openAnAccountService.addNewClientToBank(openAnAccountForm, bank);
		
		assertEquals(1, bank.getClientList().size());
	}
	
	@Test
	public void cannotAddClientWithSameEmailOrUsernameIfAlreadyExistInBank() {
		
		bank.getClientList().add(new Client("Tom", "Grass", "artibob@wp.pl",
				new char[] { 'a', 'r', 't', 'i', '9', '2' }, new char[] { 'p', 'a', 's', 's', '1', '6' }));
		
		openAnAccountService.addNewClientToBank(openAnAccountForm, bank);
		
		assertEquals(1, bank.getClientList().size());
	}

}
