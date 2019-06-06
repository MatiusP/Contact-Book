package by.finalwork.contactbook.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressValidation implements ContactValidator {
	private static final String ADDRESS_REGEX = "^[0-9]*.*[À-ÿÀ-ßa-zA-Z]{1}[À-ÿÀ-ßa-zA-Z0-9\\u002E\\s,-]{1,}$";
	private static Pattern pattern = Pattern.compile(ADDRESS_REGEX);

	@Override
	public boolean isValid(String address) {
		Matcher matcher = pattern.matcher(address);
		return matcher.matches();
	}

	@Override
	public String getDataEntryRules() {
		String dataEntryRules = "Rules for entering address:  can't consist only of numbers + length at least 2 characters.";
		return dataEntryRules;
	}
}