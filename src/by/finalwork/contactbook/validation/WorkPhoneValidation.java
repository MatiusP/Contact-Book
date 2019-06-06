package by.finalwork.contactbook.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkPhoneValidation implements ContactValidator {
	private static final String WORKPHONE_REGEX = "^((\\+375)|80){1}(1|2){1}[0-9]{8}$";
	private static Pattern pattern = Pattern.compile(WORKPHONE_REGEX);

	@Override
	public boolean isValid(String workPhone) {
		Matcher matcher = pattern.matcher(workPhone);
		return matcher.matches();
	}

	@Override
	public String getDataEntryRules() {
		String dataEntryRules = "Rules for entering work phone number: '+375' or '80'   plus   '1' or '2'   plus   eight numbers.";
		return dataEntryRules;
	}
}