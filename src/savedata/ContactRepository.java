package savedata;

import java.util.List;
import by.finalwork.contactbook.Contact;

public interface ContactRepository {

	Contact contactSave(Contact contact);

	Contact contactUpdate(Contact contact);

	List<Contact> getAll();

	List<Contact> getAllUserContacts(int id);

	boolean deleteContact(int contactId);

	boolean deleteAllUserContacts(int userId);
}