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
		
		int expectedBalance = 0;
		
		assertEquals(expectedBalance, account.checkBalance(), 0.01);
	}
	
	@Test
	public void accountNumberIs26CharactersLength() {
		assertEquals(26, account.getAccountNo().length());
	}
	
	@Test
	public void positiveDepositAmountShouldBeAddedToAccountBalanceAndReturnsTrue() {
		
		double depositAmount = 34.56;
		double nextDepositAmount = 344.01;
		double sum = depositAmount + nextDepositAmount;
		
		account.deposit(depositAmount);
		account.deposit(nextDepositAmount);
		
		assertEquals(sum, account.checkBalance(), 0.01);
		assertTrue(account.deposit(depositAmount));
		assertTrue(account.deposit(nextDepositAmount));
	}
	
	@Test
	public void negativeDepositAmountGetsRejectedAndReturnsFalse() {
		
		double depositAmount = -342.87;
		
		account.deposit(depositAmount);

		assertEquals(0, account.checkBalance(), 0.01);
		assertFalse(account.deposit(depositAmount));
	}
	
	@Test
	public void depositMethodMultipliesAmountByHundred() {
		
		double depositAmount = 42.87;
		account.deposit(depositAmount);

		assertEquals(4287, account.getBalance(), 0.01);
	}
	
	@Test
	public void withdrawAllMethodReturnsAllBalanceAndSetsNewBalanceToZero() {
		
		double depositAmount = 422.87;
		double nextDepositAmount = 741.01;
		double sum = depositAmount + nextDepositAmount;
		account.deposit(depositAmount);
		account.deposit(nextDepositAmount);

		assertEquals(sum, account.withdrawAll(), 0.01);
		assertEquals(0, account.getBalance());
	}
	
	@Test
	public void withdrawShouldSubtractAmountFromBalanceAndReturnTrue() {
		
		double depositAmount = 23.87;
		double amountToWithdraw = 3.87;
		double subtract = depositAmount - amountToWithdraw;
		account.deposit(depositAmount);
		account.withdraw(amountToWithdraw);
		
		assertEquals(subtract, account.checkBalance(), 0.01);
		assertTrue(account.withdraw(amountToWithdraw));
	}
	
	@Test
	public void whenTryingToWithdrawMoreThanActualBalanceReturnFalse() {
		
		double depositAmount = 23.87;
		double amountToWithdraw = 312.87;
		account.deposit(depositAmount);
		account.withdraw(amountToWithdraw);
		
		assertFalse(account.withdraw(amountToWithdraw));
	}
	
	@Test
	public void whenTryingToWithdrawMoreThanActualBalanceThenBalanceStaysUnchanged() {
		
		double depositAmount = 23.87;
		double amountToWithdraw = 312.87;
		account.deposit(depositAmount);
		account.withdraw(amountToWithdraw);
		
		assertEquals(depositAmount, account.checkBalance(), 0.01);
	}
	
	@Test
	public void whenTryingToWithdrawNegativeAmountThenReturnFalse() {
		
		double amountToWithdraw = -312.87;
		assertFalse(account.withdraw(amountToWithdraw));
	}

}
