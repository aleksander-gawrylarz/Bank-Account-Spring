package com.bank;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.bank.userUI.LoginUI;

@SpringBootApplication
public class BankAccountApplication {

	public static void main(String[] args) {
		
		ApplicationContext applicationContext = SpringApplication.run(BankAccountApplication.class, args);
		
		LoginUI loginUI = applicationContext.getBean(LoginUI.class);
		loginUI.loginPage();
	}
}
