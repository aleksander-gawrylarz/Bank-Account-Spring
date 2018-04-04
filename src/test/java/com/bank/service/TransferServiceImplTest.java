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
public class TransferServiceImplTest {

	@Autowired
	private TransferService transferService;

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
		
		checkingAccountService.createNewAccount("PLN");
		checkingAccountService.createNewAccount("PLN");
		checkingAccountService.createNewAccount("EUR");
		
		bankDAO.getClientAccounts().get(0).setAccountName("Account 1");
		bankDAO.getClientAccounts().get(1).setAccountName("Account 2");
		bankDAO.getClientAccounts().get(2).setAccountName("Account 3");
		bankDAO.getClientAccounts().get(0).withdrawAll();
		bankDAO.getClientAccounts().get(1).withdrawAll();
		bankDAO.getClientAccounts().get(2).withdrawAll();
		transferService.deposit("Account 1", 100);
		
		bankDAO.getClientTransactions().clear();
	}

	@Test
	public void checkIfNamesAreSetForAccounts() {
		
		assertTrue(bankDAO.getClientAccounts().get(0).getAccountName().equals("Account 1"));
		assertTrue(bankDAO.getClientAccounts().get(1).getAccountName().equals("Account 2"));
		assertTrue(bankDAO.getClientAccounts().get(2).getAccountName().equals("Account 3"));
	}
	
	@Test
	public void checkIfBalanceIsSetForAccounts() {
		
		assertEquals(100, bankDAO.getClientAccounts().get(0).checkBalance(), 0.01);
		assertEquals(0, bankDAO.getClientAccounts().get(1).checkBalance(), 0.01);
		assertEquals(0, bankDAO.getClientAccounts().get(2).checkBalance(), 0.01);
	}
	
	@Test
	public void checkIfCurrencyIsSetForAccounts() {
		
		assertTrue(bankDAO.getClientAccounts().get(0).getCurrency().equals("PLN"));
		assertTrue(bankDAO.getClientAccounts().get(1).getCurrency().equals("PLN"));
		assertTrue(bankDAO.getClientAccounts().get(2).getCurrency().equals("EUR"));
	}
	
	@Test
	public void transferShouldDecreaseSenderBalanceAndIncreaseRecipientBalanceByGivenAmount() {
		
		String sender = "Account 1";
		String recipient = "Account 2";
		double amount = 20;
		
		transferService.transfer(sender, recipient, amount);
		assertEquals(80, bankDAO.getAccountByName("Account 1").checkBalance(), 0.01);
		assertEquals(20, bankDAO.getAccountByName("Account 2").checkBalance(), 0.01);
	}
	
	@Test
	public void whenNegativeAmountIsPassedForTransferThanBalanceIsUnchanged() {
		
		String sender = "Account 1";
		String recipient = "Account 2";
		double amount = -20;
		
		transferService.transfer(sender, recipient, amount);
		assertEquals(100, bankDAO.getAccountByName("Account 1").checkBalance(), 0.01);
		assertEquals(0, bankDAO.getAccountByName("Account 2").checkBalance(), 0.01);
	}
	
	@Test
	public void whenTryingToTransferMoreMoneyThanActualBalanceThenBalanceIsUnchanged() {
		
		String sender = "Account 1";
		String recipient = "Account 2";
		double amount = 3000;
		
		transferService.transfer(sender, recipient, amount);
		assertEquals(100, bankDAO.getAccountByName("Account 1").checkBalance(), 0.01);
		assertEquals(0, bankDAO.getAccountByName("Account 2").checkBalance(), 0.01);
	}
	
	@Test
	public void whenNotExistingAccountNameIsPassedThenBalanceIsUnchanged() {
		
		String sender = "Account 75";
		String recipient = "Account 2";
		double amount = 20;
		
		transferService.transfer(sender, recipient, amount);
		
		sender = "Account 23";
		recipient = "Account 12";
		amount = 20;
		
		transferService.transfer(sender, recipient, amount);
		
		assertEquals(100, bankDAO.getAccountByName("Account 1").checkBalance(), 0.01);
		assertEquals(0, bankDAO.getAccountByName("Account 2").checkBalance(), 0.01);
	}
	
	@Test
	public void foreignTransferShouldDecreaseSenderBalanceAndIncreaseRecipientBalanceByExchangeRate() {
		
		// 1 EUR = 4.2 PLN
		
		String sender = "Account 1";
		String recipient = "Account 3";
		double amount = 4.2;
		
		// PLN to EUR
		transferService.transfer(sender, recipient, amount);
		assertEquals(95.8, bankDAO.getAccountByName("Account 1").checkBalance(), 0.01);
		assertEquals(1.0, bankDAO.getAccountByName("Account 3").checkBalance(), 0.01);
		
		sender = "Account 1";
		recipient = "Account 3";
		amount = 15.23;
		
		// PLN to EUR
		transferService.transfer(sender, recipient, amount);
		assertEquals(80.57, bankDAO.getAccountByName("Account 1").checkBalance(), 0.01);
		assertEquals(4.62, bankDAO.getAccountByName("Account 3").checkBalance(), 0.01);
		
		sender = "Account 3";
		recipient = "Account 1";
		amount = 2;
		
		// EUR to PLN
		transferService.transfer(sender, recipient, amount);
		assertEquals(88.97, bankDAO.getAccountByName("Account 1").checkBalance(), 0.01);
		assertEquals(2.62, bankDAO.getAccountByName("Account 3").checkBalance(), 0.01);
	}
	
	@Test
	public void afterSuccessfulTransferTransactionListSizeShouldBeTwo() {
		
		String sender = "Account 1";
		String recipient = "Account 2";
		double amount = 20;
		
		transferService.transfer(sender, recipient, amount);
		
		assertEquals(2, bankDAO.getClientTransactions().size());
	}
	
	@Test
	public void transactionVariablesShouldMatchValuesFromTransfer() {
		
		String sender = "Account 1";
		String recipient = "Account 2";
		double amount = 20;
		
		transferService.transfer(sender, recipient, amount);
		
		assertEquals(-20, bankDAO.getClientTransactions().get(0).getAmountOfMoney(), 0.01);
		assertEquals(80, bankDAO.getClientTransactions().get(0).getBalanceAfter(), 0.01);
		
		assertEquals(20, bankDAO.getClientTransactions().get(1).getAmountOfMoney(), 0.01);
		assertEquals(20, bankDAO.getClientTransactions().get(1).getBalanceAfter(), 0.01);
		
		assertTrue(bankDAO.getClientTransactions().get(0).getCurrency().equals("PLN"));
		assertTrue(bankDAO.getClientTransactions().get(1).getCurrency().equals("PLN"));
		
		assertNotNull(bankDAO.getClientTransactions().get(0).getDateOfTransaction());
		assertNotNull(bankDAO.getClientTransactions().get(1).getDateOfTransaction());
		
		assertFalse(bankDAO.getClientTransactions().get(0).getSenderAccountNo().
				equals(bankDAO.getClientTransactions().get(1).getRecipientAccountNo()));
	}
	
	@Test
	public void eachSuccessfulTransferChangesTransactionBalanceAfterValue() {
		
		String sender = "Account 1";
		String recipient = "Account 2";
		
		transferService.transfer(sender, recipient, 20);
		transferService.transfer(sender, recipient, 2.5);
		transferService.transfer(sender, recipient, 1);

		assertEquals(80, bankDAO.getClientTransactions().get(0).getBalanceAfter(), 0.01);
		assertEquals(20, bankDAO.getClientTransactions().get(1).getBalanceAfter(), 0.01);
		assertEquals(77.5, bankDAO.getClientTransactions().get(2).getBalanceAfter(), 0.01);
		assertEquals(22.5, bankDAO.getClientTransactions().get(3).getBalanceAfter(), 0.01);
		assertEquals(76.5, bankDAO.getClientTransactions().get(4).getBalanceAfter(), 0.01);
		assertEquals(23.5, bankDAO.getClientTransactions().get(5).getBalanceAfter(), 0.01);
	}
	
	@Test
	public void transactionVariablesShouldMatchValuesFromForeignTransfer() {
		
		// 1 EUR = 4.2 PLN
		
		String sender = "Account 1";
		String recipient = "Account 3";
		double amount = 4.2;
		
		transferService.transfer(sender, recipient, amount);
		
		assertEquals(95.8, bankDAO.getClientTransactions().get(0).getBalanceAfter(), 0.01);
		assertEquals(1, bankDAO.getClientTransactions().get(1).getBalanceAfter(), 0.01);
		
		assertTrue(bankDAO.getClientTransactions().get(0).getCurrency().equals("PLN"));
		assertTrue(bankDAO.getClientTransactions().get(1).getCurrency().equals("EUR"));
	}
	
	@Test
	public void whenClientDepositToExistingAccountCorrectAmountThenBalanceIsIncreased() {
		
		String accountName = "Account 1";
		double amount = 23.56;
		transferService.deposit(accountName, amount);
		
		assertEquals(123.56, bankDAO.getAccountByName("Account 1").checkBalance(), 0.01);
	}
	
	@Test
	public void whenClientTriesToDepositNegativeAmountThenBalanceIsUnchanged() {
		
		String accountName = "Account 1";
		double amount = -100;
		transferService.deposit(accountName, amount);
		
		assertEquals(100, bankDAO.getAccountByName("Account 1").checkBalance(), 0.01);
		
	}

	@Test
	public void whenClientWithdrawsfromExistingAccountCorrectAmountThenBalanceIsDecreased() {
		
		String accountName = "Account 1";
		double amount = 0.56;
		transferService.withdraw(accountName, amount);
		
		assertEquals(99.44, bankDAO.getAccountByName("Account 1").checkBalance(), 0.01);
	}
	
	@Test
	public void whenClientTriesToWithdrawMoreMoneyThenActualBalanceThenBalanceIsUnchanged() {

		String accountName = "Account 1";
		double amount = 1000;
		transferService.withdraw(accountName, amount);
		
		assertEquals(100, bankDAO.getAccountByName("Account 1").checkBalance(), 0.01);
	}
	
	@Test
	public void whenClientTriesToWithdrawNegativeAmountThenBalanceIsUnchanged() {
		
		String accountName = "Account 1";
		transferService.withdraw(accountName, -10);
		
		assertEquals(100, bankDAO.getAccountByName("Account 1").checkBalance(), 0.01);
	}
	
	@Test
	public void afterSuccessfulDepositTransactionListShouldNotBeEmpty() {
		
		String accountName = "Account 1";
		transferService.deposit(accountName, 13.56);
		
		assertFalse(bankDAO.getClientTransactions().isEmpty());
	}
	
	@Test
	public void afterSuccessfulDepositAndWithdrawTransactionListSizeShouldBeTwo() {
		
		String accountName = "Account 1";
		
		transferService.deposit(accountName, 23.56);
		transferService.withdraw(accountName, 3);
		
		assertEquals(2, bankDAO.getClientTransactions().size());
	}
	
	@Test
	public void transactionVariablesShouldMatchValuesFromDeposit() {
		
		String accountName = "Account 1";
		double amount = 23.56;
		transferService.deposit(accountName, amount);
		
		assertEquals(23.56, bankDAO.getClientTransactions().get(0).getAmountOfMoney(), 0.01);
		assertEquals(123.56, bankDAO.getClientTransactions().get(0).getBalanceAfter(), 0.01);
		assertTrue(bankDAO.getClientTransactions().get(0).getCurrency().equals("PLN"));
		assertNotNull(bankDAO.getClientTransactions().get(0).getDateOfTransaction());
		
		assertTrue(bankDAO.getClientTransactions().get(0).getSenderAccountNo().
				equals(bankDAO.getClientTransactions().get(0).getRecipientAccountNo()));
	}
	
	@Test
	public void transactionVariablesShouldMatchValuesFromWithdraw() {
		
		String accountName = "Account 1";
		double amount = 23.56;
		transferService.deposit(accountName, amount);
		transferService.withdraw(accountName, 3.56);
		
		assertEquals(-3.56, bankDAO.getClientTransactions().get(1).getAmountOfMoney(), 0.01);
		assertEquals(120, bankDAO.getClientTransactions().get(1).getBalanceAfter(), 0.01);
		assertTrue(bankDAO.getClientTransactions().get(1).getCurrency().equals("PLN"));
		assertNotNull(bankDAO.getClientTransactions().get(1).getDateOfTransaction());
		
		assertTrue(bankDAO.getClientTransactions().get(1).getSenderAccountNo().
				equals(bankDAO.getClientTransactions().get(1).getRecipientAccountNo()));
	}
	
	@Test
	public void withdrawSetsTransactionAmountOfMoneyWithNegativeSign() {
		
		String accountName = "Account 1";
		double amount = 20;
		
		transferService.deposit(accountName, amount);
		transferService.withdraw(accountName, amount);
		
		assertEquals(20, bankDAO.getClientTransactions().get(0).getAmountOfMoney(), 0.01);
		assertEquals(-20, bankDAO.getClientTransactions().get(1).getAmountOfMoney(), 0.01);
	}
	
	@Test
	public void eachSuccessfulDepositAddsNewTransactionToList() {
		
		String accountName = "Account 1";

		transferService.deposit(accountName, 100);
		transferService.deposit(accountName, 23.5);
		transferService.deposit(accountName, 5.10);

		assertEquals(3, bankDAO.getClientTransactions().size());
	}
	
	@Test
	public void eachSuccessfulDepositIncreasesTransactionBalanceAfterValue() {
		
		String accountName = "Account 1";
		
		transferService.deposit(accountName, 100);
		transferService.deposit(accountName, 23.5);
		transferService.deposit(accountName, 5.10);

		assertEquals(200, bankDAO.getClientTransactions().get(0).getBalanceAfter(), 0.01);
		assertEquals(223.5, bankDAO.getClientTransactions().get(1).getBalanceAfter(), 0.01);
		assertEquals(228.6, bankDAO.getClientTransactions().get(2).getBalanceAfter(), 0.01);
	}
	
	@Test
	public void afterFailedDepositTransactionListShouldBeEmpty() {
		
		String accountName = "Account 1";
		transferService.deposit(accountName, -20);
		
		assertTrue(bankDAO.getClientTransactions().isEmpty());
	}
	
	@Test
	public void afterFailedWithdrawTransactionListSizeShouldNotBeChanged() {
		
		String accountName = "Account 1";

		transferService.withdraw(accountName, 1000);
		transferService.withdraw(accountName, -100);
		
		assertEquals(0, bankDAO.getClientTransactions().size());
	}
}
