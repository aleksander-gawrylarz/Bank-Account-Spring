package com.bank.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;

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
		transferService.deposit("Account 1", new BigDecimal("100.00"));
		
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
		
		assertTrue(bankDAO.getClientAccounts().get(0).checkBalance().equals(new BigDecimal("100.00")));
		assertTrue(bankDAO.getClientAccounts().get(1).checkBalance().equals(new BigDecimal("0.00")));
		assertTrue(bankDAO.getClientAccounts().get(2).checkBalance().equals(new BigDecimal("0.00")));
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
		BigDecimal amount = new BigDecimal("20.00");
		
		transferService.transfer(sender, recipient, amount);
		assertTrue(bankDAO.getAccountByName("Account 1").checkBalance().equals(new BigDecimal("80.00")));
		assertTrue(bankDAO.getAccountByName("Account 2").checkBalance().equals(new BigDecimal("20.00")));
	}
	
	@Test
	public void whenNegativeAmountIsPassedForTransferThanBalanceIsUnchanged() {
		
		String sender = "Account 1";
		String recipient = "Account 2";
		BigDecimal amount = new BigDecimal("-20.00");
		
		transferService.transfer(sender, recipient, amount);
		assertTrue(bankDAO.getAccountByName("Account 1").checkBalance().equals(new BigDecimal("100.00")));
		assertTrue(bankDAO.getAccountByName("Account 2").checkBalance().equals(new BigDecimal("0.00")));
	}
	
	@Test
	public void whenTryingToTransferMoreMoneyThanActualBalanceThenBalanceIsUnchanged() {
		
		String sender = "Account 1";
		String recipient = "Account 2";
		BigDecimal amount = new BigDecimal("3000.00");
		
		transferService.transfer(sender, recipient, amount);
		assertTrue(bankDAO.getAccountByName("Account 1").checkBalance().equals(new BigDecimal("100.00")));
		assertTrue(bankDAO.getAccountByName("Account 2").checkBalance().equals(new BigDecimal("0.00")));
	}
	
	@Test
	public void whenNotExistingAccountNameIsPassedThenBalanceIsUnchanged() {
		
		String sender = "Account 75";
		String recipient = "Account 2";
		BigDecimal amount = new BigDecimal("20.00");
		
		transferService.transfer(sender, recipient, amount);
		
		sender = "Account 23";
		recipient = "Account 12";
		amount = new BigDecimal("25.46");
		
		transferService.transfer(sender, recipient, amount);
		
		assertTrue(bankDAO.getAccountByName("Account 1").checkBalance().equals(new BigDecimal("100.00")));
		assertTrue(bankDAO.getAccountByName("Account 2").checkBalance().equals(new BigDecimal("0.00")));
	}
	
	@Test
	public void foreignTransferPLNtoEUR() {
		
		// 1 EUR = 4.2 PLN
		
		String sender = "Account 1";
		String recipient = "Account 3";
		BigDecimal amount = new BigDecimal("15.45");
		
		// PLN to EUR
		transferService.transfer(sender, recipient, amount);
		assertEquals(0, bankDAO.getAccountByName("Account 1").checkBalance().compareTo(new BigDecimal("84.55")));
		assertEquals(0, bankDAO.getAccountByName("Account 3").checkBalance().compareTo(new BigDecimal("3.71")));
	}
	
	@Test
	public void foreignTransferEURtoPLN() {
		
		// 1 EUR = 4.2 PLN
		
		bankDAO.getAccountByName("Account 1").withdrawAll();
		transferService.deposit("Account 3", new BigDecimal("100.00"));
		
		String sender = "Account 3";
		String recipient = "Account 1";
		BigDecimal amount = new BigDecimal("3.79");
		
		// EUR to PLN
		transferService.transfer(sender, recipient, amount);
		assertEquals(0, bankDAO.getAccountByName("Account 3").checkBalance().compareTo(new BigDecimal("96.21")));
		assertEquals(0, bankDAO.getAccountByName("Account 1").checkBalance().compareTo(new BigDecimal("15.92")));
	}
	
	@Test
	public void afterSuccessfulTransferTransactionListSizeShouldBeTwo() {
		
		String sender = "Account 1";
		String recipient = "Account 2";
		BigDecimal amount = new BigDecimal("20");
		
		transferService.transfer(sender, recipient, amount);
		
		assertEquals(2, bankDAO.getClientTransactions().size());
	}
	
	@Test
	public void whenPassingZeroAsAmountForTransferTransactionListSizeShouldNotBeChanged() {
		
		String sender = "Account 1";
		String recipient = "Account 2";
		
		transferService.transfer(sender, recipient, new BigDecimal("0"));
		transferService.transfer(sender, recipient, BigDecimal.ZERO);
		
		assertEquals(0, bankDAO.getClientTransactions().size());
	}
	
	@Test
	public void whenPassingZeroAsAmountForDepositTransactionListSizeShouldNotBeChanged() {
		
		String account = "Account 1";
		
		transferService.deposit(account, new BigDecimal("0"));
		transferService.deposit(account, BigDecimal.ZERO);
		
		assertEquals(0, bankDAO.getClientTransactions().size());
	}
	
	@Test
	public void transactionVariablesShouldMatchValuesFromTransfer() {
		
		String sender = "Account 1";
		String recipient = "Account 2";
		BigDecimal amount = new BigDecimal("20.00");
		
		transferService.transfer(sender, recipient, amount);
		
		assertEquals(0, bankDAO.getClientTransactions().get(0).getAmountOfMoney().compareTo(new BigDecimal("-20")));
		assertEquals(0, bankDAO.getClientTransactions().get(0).getBalanceAfter().compareTo(new BigDecimal("80")));
		
		assertEquals(0, bankDAO.getClientTransactions().get(1).getAmountOfMoney().compareTo(new BigDecimal("20")));
		assertEquals(0, bankDAO.getClientTransactions().get(1).getBalanceAfter().compareTo(new BigDecimal("20")));
		
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
		
		transferService.transfer(sender, recipient, new BigDecimal("20.00"));
		transferService.transfer(sender, recipient, new BigDecimal("2.50"));
		transferService.transfer(sender, recipient, new BigDecimal("1.00"));

		assertEquals(0, bankDAO.getClientTransactions().get(0).getBalanceAfter().compareTo(new BigDecimal("80")));
		assertEquals(0, bankDAO.getClientTransactions().get(1).getBalanceAfter().compareTo(new BigDecimal("20")));
		assertEquals(0, bankDAO.getClientTransactions().get(2).getBalanceAfter().compareTo(new BigDecimal("77.5")));
		assertEquals(0, bankDAO.getClientTransactions().get(3).getBalanceAfter().compareTo(new BigDecimal("22.5")));
		assertEquals(0, bankDAO.getClientTransactions().get(4).getBalanceAfter().compareTo(new BigDecimal("76.5")));
		assertEquals(0, bankDAO.getClientTransactions().get(5).getBalanceAfter().compareTo(new BigDecimal("23.5")));
	}
	
	@Test
	public void transactionVariablesShouldMatchValuesFromForeignTransfer() {
		
		// 1 EUR = 4.2 PLN
		
		String sender = "Account 1";
		String recipient = "Account 3";
		BigDecimal amount = new BigDecimal("15.45");
		
		transferService.transfer(sender, recipient, amount);
		
		assertEquals(0, bankDAO.getClientTransactions().get(0).getBalanceAfter().compareTo(new BigDecimal("84.55")));
		assertEquals(0, bankDAO.getClientTransactions().get(1).getBalanceAfter().compareTo(new BigDecimal("3.71")));
		
		assertTrue(bankDAO.getClientTransactions().get(0).getCurrency().equals("PLN"));
		assertTrue(bankDAO.getClientTransactions().get(1).getCurrency().equals("EUR"));
	}
	
	@Test
	public void whenClientDepositToExistingAccountCorrectAmountThenBalanceIsIncreased() {
		
		String accountName = "Account 1";
		BigDecimal amount = new BigDecimal("23.56");
		transferService.deposit(accountName, amount);
		
		assertEquals(0, bankDAO.getAccountByName("Account 1").checkBalance().compareTo(new BigDecimal("123.56")));
	}
	
	@Test
	public void whenClientTriesToDepositNegativeAmountThenBalanceIsUnchanged() {
		
		String accountName = "Account 1";
		BigDecimal amount = new BigDecimal("-150.00");
		transferService.deposit(accountName, amount);
		
		assertEquals(0, bankDAO.getAccountByName("Account 1").checkBalance().compareTo(new BigDecimal("100.00")));
		
	}

	@Test
	public void whenClientWithdrawsfromExistingAccountCorrectAmountThenBalanceIsDecreased() {
		
		String accountName = "Account 1";
		BigDecimal amount = new BigDecimal("0.56");
		transferService.withdraw(accountName, amount);
		
		assertEquals(0, bankDAO.getAccountByName("Account 1").checkBalance().compareTo(new BigDecimal("99.44")));
	}
	
	@Test
	public void whenClientTriesToWithdrawMoreMoneyThenActualBalanceThenBalanceIsUnchanged() {

		String accountName = "Account 1";
		BigDecimal amount = new BigDecimal("1000");
		transferService.withdraw(accountName, amount);
		
		assertEquals(0, bankDAO.getAccountByName("Account 1").checkBalance().compareTo(new BigDecimal("100.00")));
	}
	
	@Test
	public void whenClientTriesToWithdrawNegativeAmountThenBalanceIsUnchanged() {
		
		String accountName = "Account 1";
		BigDecimal amount = new BigDecimal("-10");
		transferService.withdraw(accountName, amount);
		
		assertEquals(0, bankDAO.getAccountByName("Account 1").checkBalance().compareTo(new BigDecimal("100.00")));
	}
	
	@Test
	public void afterSuccessfulDepositTransactionListShouldNotBeEmpty() {
		
		String accountName = "Account 1";
		BigDecimal amount = new BigDecimal("13.56");
		transferService.deposit(accountName, amount);
		
		assertFalse(bankDAO.getClientTransactions().isEmpty());
	}
	
	@Test
	public void afterSuccessfulDepositAndWithdrawTransactionListSizeShouldBeTwo() {
		
		String accountName = "Account 1";
		
		transferService.deposit(accountName, new BigDecimal("23.56"));
		transferService.withdraw(accountName, new BigDecimal("3"));
		
		assertEquals(2, bankDAO.getClientTransactions().size());
	}
	
	@Test
	public void transactionVariablesShouldMatchValuesFromDeposit() {
		
		String accountName = "Account 1";
		BigDecimal amount = new BigDecimal("23.56");
		transferService.deposit(accountName, amount);
		
		assertEquals(0, bankDAO.getClientTransactions().get(0).getAmountOfMoney().compareTo(new BigDecimal("23.56")));
		assertEquals(0, bankDAO.getClientTransactions().get(0).getBalanceAfter().compareTo(new BigDecimal("123.56")));
		assertTrue(bankDAO.getClientTransactions().get(0).getCurrency().equals("PLN"));
		assertNotNull(bankDAO.getClientTransactions().get(0).getDateOfTransaction());
		
		assertTrue(bankDAO.getClientTransactions().get(0).getSenderAccountNo().
				equals(bankDAO.getClientTransactions().get(0).getRecipientAccountNo()));
	}
	
	@Test
	public void transactionVariablesShouldMatchValuesFromWithdraw() {
		
		String accountName = "Account 1";
		BigDecimal amount = new BigDecimal("23.56");
		transferService.deposit(accountName, amount);
		transferService.withdraw(accountName, new BigDecimal("3.56"));
		
		assertEquals(0, bankDAO.getClientTransactions().get(1).getAmountOfMoney().compareTo(new BigDecimal("-3.56")));
		assertEquals(0, bankDAO.getClientTransactions().get(1).getBalanceAfter().compareTo(new BigDecimal("120.00")));
		assertTrue(bankDAO.getClientTransactions().get(1).getCurrency().equals("PLN"));
		assertNotNull(bankDAO.getClientTransactions().get(1).getDateOfTransaction());
		
		assertTrue(bankDAO.getClientTransactions().get(1).getSenderAccountNo().
				equals(bankDAO.getClientTransactions().get(1).getRecipientAccountNo()));
	}
	
	@Test
	public void withdrawSetsTransactionAmountOfMoneyWithNegativeSign() {
		
		String accountName = "Account 1";
		BigDecimal amount = new BigDecimal("23.56");
		
		transferService.deposit(accountName, amount);
		transferService.withdraw(accountName, amount);
		
		assertEquals(0, bankDAO.getClientTransactions().get(0).getAmountOfMoney().compareTo(new BigDecimal("23.56")));
		assertEquals(0, bankDAO.getClientTransactions().get(1).getAmountOfMoney().compareTo(new BigDecimal("-23.56")));
	}
	
	@Test
	public void eachSuccessfulDepositAddsNewTransactionToList() {
		
		String accountName = "Account 1";

		transferService.deposit(accountName, new BigDecimal("100"));
		transferService.deposit(accountName, new BigDecimal("23.5"));
		transferService.deposit(accountName, new BigDecimal("5.10"));

		assertEquals(3, bankDAO.getClientTransactions().size());
	}
	
	@Test
	public void eachSuccessfulDepositIncreasesTransactionBalanceAfterValue() {
		
		String accountName = "Account 1";
		
		transferService.deposit(accountName, new BigDecimal("100"));
		transferService.deposit(accountName, new BigDecimal("23.5"));
		transferService.deposit(accountName, new BigDecimal("5.10"));

		assertEquals(0, bankDAO.getClientTransactions().get(0).getBalanceAfter().compareTo(new BigDecimal("200")));
		assertEquals(0, bankDAO.getClientTransactions().get(1).getBalanceAfter().compareTo(new BigDecimal("223.5")));
		assertEquals(0, bankDAO.getClientTransactions().get(2).getBalanceAfter().compareTo(new BigDecimal("228.6")));
	}
	
	@Test
	public void afterFailedDepositTransactionListShouldBeEmpty() {
		
		String accountName = "Account 1";
		transferService.deposit(accountName, new BigDecimal("-20"));
		
		assertTrue(bankDAO.getClientTransactions().isEmpty());
	}
	
	@Test
	public void afterFailedWithdrawTransactionListSizeShouldNotBeChanged() {
		
		String accountName = "Account 1";

		transferService.withdraw(accountName, new BigDecimal("1000"));
		transferService.withdraw(accountName, new BigDecimal("-20"));
		
		assertEquals(0, bankDAO.getClientTransactions().size());
	}
}
