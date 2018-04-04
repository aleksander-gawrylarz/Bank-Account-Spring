package com.bank.entity;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.BankAccountApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=BankAccountApplication.class)
public class TransactionTest {
	
	@Autowired
	private Transaction transaction;

	@Test
	public void afterSetDateIsCalledThenDateOfTransactionIsNotNull() {
		
		transaction.setDateOfTransaction();
		
		assertNotNull(transaction.getDateOfTransaction());
	}
}
