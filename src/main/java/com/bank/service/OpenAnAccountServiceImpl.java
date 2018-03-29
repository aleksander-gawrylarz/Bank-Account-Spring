package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.Utility;
import com.bank.dao.BankDAO;
import com.bank.entity.Client;
import com.bank.form.OpenAnAccountForm;

@Service
public class OpenAnAccountServiceImpl implements OpenAnAccountService {
	
	@Autowired
	private OpenAnAccountForm openAnAccountForm;
	
	@Autowired
	private BankDAO bankDAO;
	
	@Override
	public void addNewClientToBank(OpenAnAccountForm clientForm) {

		if (!bankDAO.getClients().stream().anyMatch(element -> element.validateUserName(clientForm.getUsername())
				|| element.getEmail().equals(clientForm.getEmail()))) {

			bankDAO.getClients().add(new Client(clientForm.getFirstname(), clientForm.getLastname(),
					clientForm.getEmail(), clientForm.getUsername(), clientForm.getPassword()));
			Utility.log().info("Your Account Has Been Created");
		} else
			Utility.log().info("Client with same username or email already exist");
	}

	@Override
	public void enterFirstname(String firstname) {
		openAnAccountForm.setFirstname(firstname);
	}

	@Override
	public void enterLastname(String lastname) {
		openAnAccountForm.setLastname(lastname);		
	}

	@Override
	public void enterEmail(String email) {
		openAnAccountForm.setEmail(email);
	}

	@Override
	public void enterUsername(char[] username) {
		openAnAccountForm.setUsername(username);
	}

	@Override
	public void enterPassword(char[] password) {
		openAnAccountForm.setPassword(password);
	}

	@Override
	public OpenAnAccountForm getOpenAnAccountForm() {
		return openAnAccountForm;
	}
	
}
