package by.finalwork.contactbook.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompanyNameValidation implements ContactValidator {
	private static final String COMPANYNAME_REGEX = "^(\\d{1,})?[À-ÿÀ-ßa-zA-Z-\\s]{1,}[.[^(,\\u002E!@$%^&*()+)]]+$";
	private static Pattern pattern = Pattern.compile(COMPANYNAME_REGEX);

	@Override
	public boolean isValid(String companyName) {
		Matcher matcher = pattern.matcher(companyName);
		return matcher.matches();
	}

	@Override
	public String getDataEntryRules() {
		String dataEntryRules = "Rules for entering company name:  can't consist only of numbers + length at least 2 characters.";
		return dataEntryRules;
	}
}