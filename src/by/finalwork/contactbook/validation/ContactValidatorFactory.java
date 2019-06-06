package by.finalwork.contactbook.validation;

public class ContactValidatorFactory {

	public ContactValidator getValidator(ContactType contactType) {
		ContactValidator validator = null;
		switch (contactType) {
		case E_MAIL:
			validator = new EmailValidation();
			break;
		case MOBILE_PHONE:
			validator = new MobilePhoneValidation();
			break;
		case COMPANY_NAME:
			validator = new CompanyNameValidation();
			break;
		case ADDRESS:
			validator = new AddressValidation();
			break;
		case WORK_PHONE:
			validator = new WorkPhoneValidation();
			break;
		default:
			throw new IllegalArgumentException("Wrong contact type:" + contactType);
		}
		return validator;
	}
}