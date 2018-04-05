package com.bank.entity;

import static org.junit.Assert.*;

import java.math.BigDecimal;

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

		assertEquals(0, account.checkBalance().compareTo(BigDecimal.ZERO));
	}
	
	@Test
	public void accountNumberIs26CharactersLength() {
		assertEquals(26, account.getAccountNo().length());
	}
	
	@Test
	public void positiveDepositAmountShouldBeAddedToAccountBalance() {

		account.deposit(new BigDecimal("12.12"));
		account.deposit(new BigDecimal("10"));
		
		assertTrue(account.checkBalance().equals(new BigDecimal("22.12")));
		
	}
	
	@Test
	public void positiveDepositReturnsTrue() {
		
		assertTrue(account.deposit(new BigDecimal("20")));
	}
	
	@Test
	public void negativeDepositIsNotAddedToAccountBalance() {

		account.deposit(new BigDecimal("-324.56"));

		assertEquals(0, account.checkBalance().compareTo(BigDecimal.ZERO));
	}
	
	@Test
	public void negativeDepositReturnsFalse() {

		assertFalse(account.deposit(new BigDecimal("-12")));
	}
	
	@Test
	public void withdrawAllMethodReturnsAllBalance() {
		
		account.deposit(new BigDecimal("13.12"));
		account.deposit(new BigDecimal("10.67"));

		assertTrue(account.withdrawAll().equals(new BigDecimal("23.79")));
	}
	
	@Test
	public void withdrawAllMethodSetsNewBalanceToZero() {
		
		account.deposit(new BigDecimal("13.12"));
		account.deposit(new BigDecimal("10.67"));
		
		account.withdrawAll();

		assertEquals(0, account.checkBalance().compareTo(BigDecimal.ZERO));
	}
	
	@Test
	public void withdrawShouldSubtractAmountFromBalanceAndReturnTrue() {
		
		account.deposit(new BigDecimal("34.12"));
		account.withdraw(new BigDecimal("4.10"));
		
		assertTrue(account.checkBalance().equals(new BigDecimal("30.02")));
		assertTrue(account.withdraw(new BigDecimal("6.67")));
	}
	
	@Test
	public void whenTryingToWithdrawMoreThanActualBalanceReturnFalse() {
		
		account.deposit(new BigDecimal("100.23"));
		
		assertFalse(account.withdraw(new BigDecimal("200")));
	}
	
	@Test
	public void whenTryingToWithdrawMoreThanActualBalanceThenBalanceStaysUnchanged() {
		
		account.deposit(new BigDecimal("20.10"));
		account.withdraw(new BigDecimal("100"));
		
		assertTrue(account.checkBalance().equals(new BigDecimal("20.10")));
	}
	
	@Test
	public void whenTryingToWithdrawNegativeAmountThenReturnFalse() {
		
		assertFalse(account.withdraw(new BigDecimal("-312")));
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
