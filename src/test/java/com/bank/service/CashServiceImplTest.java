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
public class CashServiceImplTest {
	
	@Autowired
	private CashService cashService;
	
	@Autowired
	private BankDAO bankDAO;
	
	@Before
	public void setUp() throws Exception {
		
		bankDAO.getClients().add(new Client("John", "Doe", "johnD@gmail.com", new char[] {'j','o','h','n','7','5'}, 
				new char[] {'p','a','s','s','7','5'}));
		
		bankDAO.getLoginForm().setUsername(new char[] {'j','o','h','n','7','5'});
		bankDAO.getLoginForm().setPassword(new char[] {'p','a','s','s','7','5'});
		
		bankDAO.getClients().get(0).getAccountsList().get(0).withdrawAll();
	}

	@Test
	public void whenClientDepositToExistingAccountCorrectAmountThenBalanceIsIncreased() {
		
		List<String> input = Arrays.asList("Account 1", "23.56");
		cashService.deposit(input);
		
		assertEquals(23.56, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
		
	}
	
	@Test
	public void whenClientTriesToPassIncorrectTypeOfAmountForDepositThenBalanceIsUnchanged() {
		
		List<String> input = Arrays.asList("Account 1", "my money");
		cashService.deposit(input);
		
		assertEquals(0, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
		
	}
	
	@Test
	public void whenClientTriesToDepositNegativeAmountThenBalanceIsUnchanged() {
		
		List<String> negativeAmount = Arrays.asList("Account 1", "-100");
		cashService.deposit(negativeAmount);
		
		assertEquals(0, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
		
	}

	@Test
	public void whenClientWithdrawsfromExistingAccountCorrectAmountThenBalanceIsDecreased() {
		
		List<String> depositInput = Arrays.asList("Account 1", "23.56");
		cashService.deposit(depositInput);
		
		List<String> withdrawInput = Arrays.asList("Account 1", "0.56");
		cashService.withdraw(withdrawInput);
		
		assertEquals(23, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
	}
	
	@Test
	public void whenClientTriesToPassIncorrectTypeOfAmountForWithdrawThenBalanceIsUnchanged() {
		
		List<String> depositInput = Arrays.asList("Account 1", "23.56");
		cashService.deposit(depositInput);
		
		List<String> withdrawInput = Arrays.asList("Account 1", "my money");
		cashService.withdraw(withdrawInput);
		
		assertEquals(23.56, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
	}
	
	@Test
	public void whenClientTriesToWithdrawMoreMoneyThenActualBalanceThenBalanceIsUnchanged() {
		
		List<String> depositInput = Arrays.asList("Account 1", "23.56");
		cashService.deposit(depositInput);
		
		List<String> withdrawInput = Arrays.asList("Account 1", "100");
		cashService.withdraw(withdrawInput);
		
		assertEquals(23.56, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
	}
	
	@Test
	public void whenClientTriesToWithdrawNegativeAmountThenBalanceIsUnchanged() {
		
		List<String> depositInput = Arrays.asList("Account 1", "23.56");
		cashService.deposit(depositInput);
		
		List<String> withdrawInput = Arrays.asList("Account 1", "-10");
		cashService.withdraw(withdrawInput);
		
		assertEquals(23.56, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
	}
	
	@Test
	public void sizeOfListPassedAsArgumentToDepositMethodMustBeTwoOtherwiseBalanceIsUnchanged() {
		
		List<String> input = Arrays.asList("Account 1", "23.56", "Account 2");
		List<String> emptyList = new ArrayList<>();
		
		cashService.deposit(input);
		cashService.deposit(emptyList);
		
		assertEquals(0, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
	}
	
	@Test
	public void sizeOfListPassedAsArgumentToWithdrawMethodMustBeTwoOtherwiseBalanceIsUnchanged() {
		
		List<String> depositInput = Arrays.asList("Account 1", "23.56");
		cashService.deposit(depositInput);
		
		List<String> withdrawInput = Arrays.asList("Account 1", "0.56", "12");
		List<String> emptyList = new ArrayList<>();
		
		cashService.withdraw(withdrawInput);
		cashService.withdraw(emptyList);
		
		assertEquals(23.56, bankDAO.getClients().get(0).getAccountsList().get(0).checkBalance(), 0.01);
	}

}
