package by.finalwork.contactbook.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobilePhoneValidation implements ContactValidator {
	private static final String MOBILEPHONE_REGEX = "^((\\+375)|80){1}(29|44|33|25){1}[0-9]{7}$";
	private static Pattern pattern = Pattern.compile(MOBILEPHONE_REGEX);

	@Override
	public boolean isValid(String mobilePhoneNumber) {
		Matcher matcher = pattern.matcher(mobilePhoneNumber.replaceAll("[\\-\\(\\)\\ ]", ""));
		return matcher.matches();
	}

	@Override
	public String getDataEntryRules() {
		String dataEntryRules = "Rules for entering mobile phone number:  '+375' or '80'  + operator cod + seven numbers.";
		return dataEntryRules;
	}
}