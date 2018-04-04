package com.bank.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.BankAccountApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=BankAccountApplication.class)
public class AccountImplTest {
	
	@Autowired
	private Account account;

	@Before
	public void setUp() throws Exception {
		account.withdrawAll();
	}

	@Test
	public void newAccountStartsWithZeroBalance() {

		assertEquals(0, account.checkBalance(), 0.01);
	}
	
	@Test
	public void accountNumberIs26CharactersLength() {
		assertEquals(26, account.getAccountNo().length());
	}
	
	@Test
	public void positiveDepositAmountShouldBeAddedToAccountBalance() {

		account.deposit(12.12);
		account.deposit(10);
		
		assertEquals(22.12, account.checkBalance(), 0.01);
		
	}
	
	@Test
	public void positiveDepositReturnsTrue() {
		
		assertTrue(account.deposit(20));
	}
	
	@Test
	public void negativeDepositIsNotAddedToAccountBalance() {

		account.deposit(-324.56);

		assertEquals(0, account.checkBalance(), 0.01);
	}
	
	@Test
	public void negativeDepositReturnsFalse() {

		assertFalse(account.deposit(-12));
	}
	
	@Test
	public void depositMethodMultipliesAmountByHundred() {
		
		double depositAmount = 42.87;
		account.deposit(depositAmount);

		assertEquals(4287, account.getBalance(), 0.01);
	}
	
	@Test
	public void withdrawAllMethodReturnsAllBalance() {
		
		account.deposit(12.12);
		account.deposit(10);

		assertEquals(22.12, account.withdrawAll(), 0.01);
	}
	
	@Test
	public void withdrawAllMethodSetsNewBalanceToZero() {
		
		account.deposit(12.12);
		account.deposit(10);
		
		account.withdrawAll();

		assertEquals(0, account.getBalance());
	}
	
	@Test
	public void withdrawShouldSubtractAmountFromBalanceAndReturnTrue() {
		
		account.deposit(34.12);
		account.withdraw(4.10);
		
		assertEquals(30.02, account.checkBalance(), 0.01);
		assertTrue(account.withdraw(4.10));
	}
	
	@Test
	public void whenTryingToWithdrawMoreThanActualBalanceReturnFalse() {
		
		account.deposit(100.23);
		
		assertFalse(account.withdraw(200));
	}
	
	@Test
	public void whenTryingToWithdrawMoreThanActualBalanceThenBalanceStaysUnchanged() {
		
		account.deposit(20.1);
		account.withdraw(100);
		
		assertEquals(20.1, account.checkBalance(), 0.01);
	}
	
	@Test
	public void whenTryingToWithdrawNegativeAmountThenReturnFalse() {
		
		assertFalse(account.withdraw(-312));
	}
	
	@Test
	public void currencyForNewAccountIsEmpty() {
		assertTrue(account.getCurrency().isEmpty());
	}
	
	@Test
	public void whenCurrencyForAccountHasBeenSetThanCannotBeChanged() {
		
		assertTrue(account.setCurrency("PLN"));
		assertTrue(account.getCurrency().equals("PLN"));
		
		assertFalse(account.setCurrency("EUR"));
		assertFalse(account.getCurrency().equals("EUR"));
	}

}
