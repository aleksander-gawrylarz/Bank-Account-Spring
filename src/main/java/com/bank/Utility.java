package com.bank;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public abstract class Utility {

	private static final Logger logger = Logger.getLogger(Utility.class);

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	
	private static final Pattern VALID_NAME_REGEX = Pattern.compile("^[A-Z]{1}[a-z]+$");
	
	private static final Pattern VALID_USERNAME_REGEX = Pattern.compile("^[A-Za-z\\d_]{4,15}$");
	
	private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^[A-Za-z\\d_!@#$%^&*]{4,25}$");
	
	public static boolean validateRegex(String line, Pattern pattern) {
		if (line != null) {
			Matcher matcher = pattern.matcher(line);
			return matcher.find();
		} else
			return false;
	}
	
	public static Scanner userInput() {
		return new Scanner(System.in);
	}

	public static Logger log() {
		return logger;
	}

	public static Pattern getValidEmailAddressRegex() {
		return VALID_EMAIL_ADDRESS_REGEX;
	}

	public static Pattern getValidNameRegex() {
		return VALID_NAME_REGEX;
	}

	public static Pattern getValidUsernameRegex() {
		return VALID_USERNAME_REGEX;
	}

	public static Pattern getValidPasswordRegex() {
		return VALID_PASSWORD_REGEX;
	}

}
