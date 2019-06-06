package by.finalwork.contactbook.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation implements ContactValidator {
	private static final String EMAIL_REGEX = "^[a-zA-Z]{1}[a-zA-Z\\d\\u002E\\u005F]+@([a-zA-Z]+\\u002E){1,2}[a-z]{1,3}$";
	private static Pattern pattern = Pattern.compile(EMAIL_REGEX);

	@Override
	public boolean isValid(String E_mail) {
		Matcher matcher = pattern.matcher(E_mail);
		return matcher.matches();
	}

	@Override
	public String getDataEntryRules() {
		String dataEntryRules = "Rules for entering e-mail:  standard data entry requirements.";
		return dataEntryRules;
	}
}