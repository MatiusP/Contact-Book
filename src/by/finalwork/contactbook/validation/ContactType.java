package by.finalwork.contactbook.validation;

public enum ContactType {
	E_MAIL(1), MOBILE_PHONE(2), COMPANY_NAME(3), ADDRESS(4), WORK_PHONE(5);

	ContactType(int order) {
		this.order = order;
	}

	int order;

	public static ContactType getByOrderCode(int code) {
		for (ContactType t : values()) {
			if (t.order == code) {
				return t;
			}
		}
		throw new IllegalArgumentException("No such element for code: " + code);
	}
}