package by.finalwork.contactbook.validation;

public interface ContactValidator {

	boolean isValid(String data);

	String getDataEntryRules();
}