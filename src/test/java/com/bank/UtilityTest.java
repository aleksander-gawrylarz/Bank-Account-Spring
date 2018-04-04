package com.bank;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilityTest {
	
	private Utility utility;

	@SuppressWarnings("static-access")
	@Test
	public void validateValidEmail() {
		
		String validEmail = "tom12@wp.pl";
		assertTrue(utility.validateRegex(validEmail, utility.getValidEmailAddressRegex()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void validateNotValidEmail() {
		
		String notValidEmail = "tom12@wp.pl ";
		String notValidEmail2 = "tom12wp.p";

		assertFalse(utility.validateRegex(notValidEmail, utility.getValidEmailAddressRegex()));
		assertFalse(utility.validateRegex(notValidEmail2, utility.getValidEmailAddressRegex()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void validateEmptyAndNullEmail() {

		String emptyEmail = "";
		String nullEmail = null;

		assertFalse(utility.validateRegex(emptyEmail, utility.getValidEmailAddressRegex()));
		assertFalse(utility.validateRegex(nullEmail, utility.getValidEmailAddressRegex()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void validateValidName() {
		
		String validName = "Tomasz";

		assertTrue(utility.validateRegex(validName, utility.getValidNameRegex()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void validateNotValidName() {

		String notValidName = "Tomasz Kowalski";
		String notValidName2 = "tomasz";
		String notValidName3 = "Tom3asz";
		String notValidName4 = "TOMasz";
		String notValidName5 = " Tomasz";
		
		assertFalse(utility.validateRegex(notValidName, utility.getValidNameRegex()));
		assertFalse(utility.validateRegex(notValidName2, utility.getValidNameRegex()));
		assertFalse(utility.validateRegex(notValidName3, utility.getValidNameRegex()));
		assertFalse(utility.validateRegex(notValidName4, utility.getValidNameRegex()));
		assertFalse(utility.validateRegex(notValidName5, utility.getValidNameRegex()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void validateEmptyAndNullName() {

		String emptyName = "";
		String nullName = null;

		assertFalse(utility.validateRegex(emptyName, utility.getValidNameRegex()));
		assertFalse(utility.validateRegex(nullName, utility.getValidNameRegex()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void validateValidUsername() {
		
		String validUsername = "Tomasz_G";
		String validUsername2 = "Tom123";
		String validUsername3 = "tom123_gruby";
		
		assertTrue(utility.validateRegex(validUsername, utility.getValidUsernameRegex()));
		assertTrue(utility.validateRegex(validUsername2, utility.getValidUsernameRegex()));
		assertTrue(utility.validateRegex(validUsername3, utility.getValidUsernameRegex()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void validateNotValidUsername() {
		
		String notValidUsername = "Tom";
		String notValidUsername2 = "Tom_is_the_best_on_the_planet";
		String notValidUsername3 = " Tomasz#!";
		
		assertFalse(utility.validateRegex(notValidUsername, utility.getValidUsernameRegex()));
		assertFalse(utility.validateRegex(notValidUsername2, utility.getValidUsernameRegex()));
		assertFalse(utility.validateRegex(notValidUsername3, utility.getValidUsernameRegex()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void validateEmptyAndNullUsername() {
		
		String emptyUsername = "";
		String nullUsername = null;

		assertFalse(utility.validateRegex(emptyUsername, utility.getValidUsernameRegex()));
		assertFalse(utility.validateRegex(nullUsername, utility.getValidUsernameRegex()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void validateValidPassword() {
		
		String validPassword = "Tomasz_G";
		String validPassword2 = "tom12_!@#$%^&*";
		
		assertTrue(utility.validateRegex(validPassword, utility.getValidPasswordRegex()));
		assertTrue(utility.validateRegex(validPassword2, utility.getValidPasswordRegex()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void validateNotValidPassword() {

		String notValidPassword = "Tom_is_the_best_on_the_planet";
		String notValidPassword2 = "Tom";
		String notValidPassword3 = "Tom{";
		
		assertFalse(utility.validateRegex(notValidPassword, utility.getValidPasswordRegex()));
		assertFalse(utility.validateRegex(notValidPassword2, utility.getValidPasswordRegex()));
		assertFalse(utility.validateRegex(notValidPassword3, utility.getValidPasswordRegex()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void validateEmptyAndNullPassword() {

		String emptyPassword = "";
		String nullPassword = null;
		
		assertFalse(utility.validateRegex(emptyPassword, utility.getValidPasswordRegex()));
		assertFalse(utility.validateRegex(nullPassword, utility.getValidPasswordRegex()));
	}
}
