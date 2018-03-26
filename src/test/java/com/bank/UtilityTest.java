package com.bank;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilityTest {
	
	private Utility utility;

	@SuppressWarnings("static-access")
	@Test
	public void validateEmail() {
		
		String validEmail = "tom12@wp.pl";
		String notValidEmail = "tom12@wp.pl ";
		String notValidEmail2 = "tom12wp.p";
		String emptyEmail = "";
		String nullEmail = null;
		
		assertTrue(utility.validateRegex(validEmail, utility.getValidEmailAddressRegex()));
		assertFalse(utility.validateRegex(notValidEmail, utility.getValidEmailAddressRegex()));
		assertFalse(utility.validateRegex(notValidEmail2, utility.getValidEmailAddressRegex()));
		assertFalse(utility.validateRegex(emptyEmail, utility.getValidEmailAddressRegex()));
		assertFalse(utility.validateRegex(nullEmail, utility.getValidEmailAddressRegex()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void validateName() {
		
		String validName = "Tomasz";
		String notValidName = "Tomasz Kowalski";
		String notValidName2 = "tomasz";
		String notValidName3 = "Tom3asz";
		String notValidName4 = "TOMasz";
		String notValidName5 = " Tomasz";
		String emptyName = "";
		String nullName = null;
		
		assertTrue(utility.validateRegex(validName, utility.getValidNameRegex()));
		
		assertFalse(utility.validateRegex(notValidName, utility.getValidNameRegex()));
		assertFalse(utility.validateRegex(notValidName2, utility.getValidNameRegex()));
		assertFalse(utility.validateRegex(notValidName3, utility.getValidNameRegex()));
		assertFalse(utility.validateRegex(notValidName4, utility.getValidNameRegex()));
		assertFalse(utility.validateRegex(notValidName5, utility.getValidNameRegex()));
		
		assertFalse(utility.validateRegex(emptyName, utility.getValidNameRegex()));
		assertFalse(utility.validateRegex(nullName, utility.getValidNameRegex()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void validateUsername() {
		
		String validUsername = "Tomasz_G";
		String validUsername2 = "Tom123";
		String validUsername3 = "tom123_gruby";
		String notValidUsername = "Tom";
		String notValidUsername2 = "Tom_is_the_best_on_the_planet";
		String notValidUsername3 = " Tomasz#!";
		String emptyUsername = "";
		String nullUsername = null;
		
		assertTrue(utility.validateRegex(validUsername, utility.getValidUsernameRegex()));
		assertTrue(utility.validateRegex(validUsername2, utility.getValidUsernameRegex()));
		assertTrue(utility.validateRegex(validUsername3, utility.getValidUsernameRegex()));
		
		assertFalse(utility.validateRegex(notValidUsername, utility.getValidUsernameRegex()));
		assertFalse(utility.validateRegex(notValidUsername2, utility.getValidUsernameRegex()));
		assertFalse(utility.validateRegex(notValidUsername3, utility.getValidUsernameRegex()));
		
		assertFalse(utility.validateRegex(emptyUsername, utility.getValidUsernameRegex()));
		assertFalse(utility.validateRegex(nullUsername, utility.getValidUsernameRegex()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void validatePassword() {
		
		String validPassword = "Tomasz_G";
		String validPassword2 = "tom12_!@#$%^&*";
		
		String notValidPassword = "Tom_is_the_best_on_the_planet";
		String notValidPassword2 = "Tom";
		String notValidPassword3 = "Tom{";
		
		String emptyPassword = "";
		String nullPassword = null;
		
		assertTrue(utility.validateRegex(validPassword, utility.getValidPasswordRegex()));
		assertTrue(utility.validateRegex(validPassword2, utility.getValidPasswordRegex()));
		
		assertFalse(utility.validateRegex(notValidPassword, utility.getValidPasswordRegex()));
		assertFalse(utility.validateRegex(notValidPassword2, utility.getValidPasswordRegex()));
		assertFalse(utility.validateRegex(notValidPassword3, utility.getValidPasswordRegex()));
		
		assertFalse(utility.validateRegex(emptyPassword, utility.getValidPasswordRegex()));
		assertFalse(utility.validateRegex(nullPassword, utility.getValidPasswordRegex()));
	}
	
//	@SuppressWarnings("static-access")
//	@Test
//	public void validateDoubleFromString() {
//		
//		String validString = "123.450";
//		//String validString2 = "0,45";
//		
//		assertTrue(utility.validateRegex(validString, utility.getValidDoubleFromStringRegex()));
//		//assertTrue(utility.validateRegex(validString2, utility.getValidDoubleFromStringRegex()));
//		
//	}
	
}
