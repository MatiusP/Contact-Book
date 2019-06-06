package by.finalwork.contactbook.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNameValidation {
	private static final String USERNAME_REGEX = "^[�-��-�a-zA-Z_]{1}[�-��-�a-zA-Z\\s]{2,}$";
	private static Pattern pattern = Pattern.compile(USERNAME_REGEX);

	public boolean isValid(String userName) {
		Matcher matcher = pattern.matcher(userName);
		return matcher.matches();
	}
}