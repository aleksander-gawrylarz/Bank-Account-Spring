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
import com.bank.form.OpenAnAccountForm;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=BankAccountApplication.class)
public class OpenBankAccountServiceImplTest {
	
	@Autowired
	private OpenBankAccountService openBankAccountService;

	@Autowired
	private OpenAnAccountForm openAnAccountForm;
	
	@Autowired
	private BankDAO bankDAO;
	
	@Before
	public void setUp() throws Exception {

		bankDAO.getClients().clear();
		
		openAnAccountForm.setFirstname("Artur");
		openAnAccountForm.setLastname("Bobas");
		openAnAccountForm.setEmail("artibob@wp.pl");
		openAnAccountForm.setUsername(new char[] { 'a', 'r', 't', 'i', '9', '2' });
		openAnAccountForm.setPassword(new char[] { 'm', 'o', 't', 'y', 'l', '6' });

	}
	
	@Test
	public void addingNewClientToEmptyBankShouldIncreaseClientSizeByOne() {
		
		openBankAccountService.addNewClientToBank(openAnAccountForm);
		
		assertEquals(1, bankDAO.getClients().size());
	}
	
	@Test
	public void cannotAddClientWithSameEmailAndUsernameIfAlreadyExistInBank() {
		
		openAnAccountForm.setFirstname("Artur");
		openAnAccountForm.setLastname("Bobas");
		openAnAccountForm.setEmail("artibob@wp.pl");
		openAnAccountForm.setUsername(new char[] { 'a', 'r', 't', 'i', '9', '2' });
		openAnAccountForm.setPassword(new char[] { 'm', 'o', 't', 'y', 'l', '6' });
		
		openBankAccountService.addNewClientToBank(openAnAccountForm);
		
		openAnAccountForm.setFirstname("Maria");
		openAnAccountForm.setLastname("Poppa");
		openAnAccountForm.setEmail("artibob@wp.pl");
		openAnAccountForm.setUsername(new char[] { 'a', 'r', 't', 'i', '9', '2' });
		openAnAccountForm.setPassword(new char[] { 'm', 'a', 'r', 'r', 'y', '6' });
		
		openBankAccountService.addNewClientToBank(openAnAccountForm);
		
		assertEquals(1, bankDAO.getClients().size());
	}
	
	@Test
	public void cannotAddClientWithSameEmailIfAlreadyExistInBank() {
		
		openAnAccountForm.setFirstname("Artur");
		openAnAccountForm.setLastname("Bobas");
		openAnAccountForm.setEmail("artibob@wp.pl");
		openAnAccountForm.setUsername(new char[] { 'a', 'r', 't', 'i', '9', '2' });
		openAnAccountForm.setPassword(new char[] { 'm', 'o', 't', 'y', 'l', '6' });
		
		openBankAccountService.addNewClientToBank(openAnAccountForm);
		
		openAnAccountForm.setFirstname("John");
		openAnAccountForm.setLastname("Kowalski");
		openAnAccountForm.setEmail("artibob@wp.pl");
		openAnAccountForm.setUsername(new char[] { 'j', 'o', 'h', 'n', '9', '3' });
		openAnAccountForm.setPassword(new char[] { 'p', 'a', 's', 's', '1', '6' });
		
		openBankAccountService.addNewClientToBank(openAnAccountForm);

		assertEquals(1, bankDAO.getClients().size());
		
	}
	
	@Test
	public void cannotAddClientWithSameUsernameIfAlreadyExistInBank() {
		
		openAnAccountForm.setFirstname("Artur");
		openAnAccountForm.setLastname("Bobas");
		openAnAccountForm.setEmail("artibob@wp.pl");
		openAnAccountForm.setUsername(new char[] { 'a', 'r', 't', 'i', '9', '2' });
		openAnAccountForm.setPassword(new char[] { 'm', 'o', 't', 'y', 'l', '6' });
		
		openBankAccountService.addNewClientToBank(openAnAccountForm);
		
		openAnAccountForm.setFirstname("John");
		openAnAccountForm.setLastname("Kowalski");
		openAnAccountForm.setEmail("john@wp.pl");
		openAnAccountForm.setUsername(new char[] { 'a', 'r', 't', 'i', '9', '2' });
		openAnAccountForm.setPassword(new char[] { 'p', 'a', 's', 's', '1', '6' });
		
		openBankAccountService.addNewClientToBank(openAnAccountForm);

		assertEquals(1, bankDAO.getClients().size());
	}

}
