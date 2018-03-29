package com.bank.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class TransferServiceImplTest {

	@Autowired
	private TransferService transferService;
	
	@Autowired
	private CashService cashService;
	
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
		
		bankDAO.getClients().get(0).getAccountsList().clear();
		
		checkingAccountService.createNewAccount("PLN");
		checkingAccountService.createNewAccount("PLN");
		checkingAccountService.createNewAccount("EUR");
		
		bankDAO.getClients().get(0).getAccountsList().get(0).setAccountName("Account 1");
		bankDAO.getClients().get(0).getAccountsList().get(1).setAccountName("Account 2");
		bankDAO.getClients().get(0).getAccountsList().get(2).setAccountName("Account 3");
		bankDAO.getClients().get(0).getAccountsList().get(0).withdrawAll();
		bankDAO.getClients().get(0).getAccountsList().get(1).withdrawAll();
		bankDAO.getClients().get(0).getAccountsList().get(2).withdrawAll();
		cashService.deposit(Arrays.asList("Account 1", "100"));
	}

	@Test
	public void checkIfNamesAreSetForAccounts() {
		
		assertTrue(bankDAO.getClients().get(0).getAccountsList().get(0).getAccountName().equals("Account 1"));
		assertTrue(bankDAO.getClients().get(0).getAccountsList().get(1).getAccountName().equals("Account 2"));
		assertTrue(bankDAO.getClients().get(0).getAccountsList().get(2).getAccountName().equals("Account 3"));
	}
	
	@Test
	public void checkIfBalanceIsSetForAccounts() {
		
		assertEquals(100, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
		assertEquals(0, bankDAO.getClients().get(0).getAccountsList().get(1).checkBalance(), 0.01);
		assertEquals(0, bankDAO.getClients().get(0).getAccountsList().get(2).checkBalance(), 0.01);
	}
	
	@Test
	public void checkIfCurrencyIsSetForAccounts() {
		
		assertTrue(bankDAO.getClients().get(0).getAccountsList().get(0).getCurrency().equals("PLN"));
		assertTrue(bankDAO.getClients().get(0).getAccountsList().get(1).getCurrency().equals("PLN"));
		assertTrue(bankDAO.getClients().get(0).getAccountsList().get(2).getCurrency().equals("EUR"));
	}
	
	@Test
	public void transferShouldDecreaseSenderBalanceAndIncreaseRecipientBalanceByGivenAmount() {
		
		List<String> input = Arrays.asList("Account 1", "Account 2", "20");
		transferService.transfer(input);
		assertEquals(80, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
		assertEquals(20, bankDAO.getClients().get(0).getAccountsList().get(1).checkBalance(), 0.01);
	}
	
	@Test
	public void whenNegativeAmountIsPassedForTransferThanBalanceIsUnchanged() {
		
		List<String> input = Arrays.asList("Account 1", "Account 2", "-20");
		transferService.transfer(input);
		assertEquals(100, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
		assertEquals(0, bankDAO.getClients().get(0).getAccountsList().get(1).checkBalance(), 0.01);
	}
	
	@Test
	public void whenWrongTypeOfAmountIsPassedForTransferThanBalanceIsUnchanged() {
		
		List<String> input = Arrays.asList("Account 1", "Account 2", "my Money");
		transferService.transfer(input);
		assertEquals(100, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
		assertEquals(0, bankDAO.getClients().get(0).getAccountsList().get(1).checkBalance(), 0.01);
	}
	
	@Test
	public void whenTryingToTransferMoreMoneyThanActualBalanceThenBalanceIsUnchanged() {
		
		List<String> input = Arrays.asList("Account 1", "Account 2", "3000");
		transferService.transfer(input);
		assertEquals(100, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
		assertEquals(0, bankDAO.getClients().get(0).getAccountsList().get(1).checkBalance(), 0.01);
	}
	
	@Test
	public void whenNotExistingAccountNameIsPassedThenBalanceIsUnchanged() {
		
		List<String> input = Arrays.asList("Account 75", "Account 2", "20");
		transferService.transfer(input);
		List<String> input2 = Arrays.asList("Account 23", "Account 12", "20");
		transferService.transfer(input2);
		
		assertEquals(100, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
		assertEquals(0, bankDAO.getClients().get(0).getAccountsList().get(1).checkBalance(), 0.01);
	}
	
	@Test
	public void sizeOfListPassedAsArgumentToTransferMethodMustBeThreeOtherwiseBalanceIsUnchanged() {
		
		List<String> input = Arrays.asList("Account 75", "Account 2", "20" , "45");
		List<String> emptyList = new ArrayList<>();
		transferService.transfer(input);
		transferService.transfer(emptyList);
		
		assertEquals(100, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
		assertEquals(0, bankDAO.getClients().get(0).getAccountsList().get(1).checkBalance(), 0.01);
	}
	
	@Test
	public void whenAccountsForTransferHaveDifferentCurrencyThanBalanceIsUnchanged() {
		
		List<String> input = Arrays.asList("Account 1", "Account 3", "20");
		transferService.transfer(input);
		assertEquals(100, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
		assertEquals(0, bankDAO.getClients().get(0).getAccountsList().get(1).checkBalance(), 0.01);
	}
	
}
