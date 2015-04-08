package com.example.we;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {


	public static boolean validateAddress(String email)
	{
		Pattern pattern = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}");
		Matcher matcher = pattern.matcher(email.toUpperCase());
		return matcher.matches();
	}

	public static boolean validatePhone(String phone) {
		Pattern pattern = Pattern.compile("[0-9]{11}");
		Matcher matcher = pattern.matcher(phone);
		return matcher.matches();
	}

	public static boolean validateLength(String genericString) {
		return (!(genericString.isEmpty()));
	}

	
	
	
}
