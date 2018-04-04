package com.bank.entity;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.BankAccountApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=BankAccountApplication.class)
public class ClientTest {
	
	@Autowired
	private Client client;

	@Test
	public void clientStartsWithEmptyTransactionList() {
		assertTrue(client.getTranscationList().isEmpty());
	}
	
	@Test
	public void clientStartsWithEmptyAccountList() {
		assertTrue(client.getAccountsList().isEmpty());
	}
}
