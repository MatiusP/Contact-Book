package by.finalwork.contactbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import by.finalwork.contactbook.validation.ContactType;
import by.finalwork.contactbook.validation.ContactValidator;
import by.finalwork.contactbook.validation.ContactValidatorFactory;
import by.finalwork.contactbook.validation.UserNameValidation;
import by.finalwork.contactbook.savedata.ContactFileReposytory;
import by.finalwork.contactbook.savedata.ContactRepository;
import by.finalwork.contactbook.savedata.UserFileRepository;
import by.finalwork.contactbook.savedata.UserRepository;

public class Menu {

	private static Scanner sc;
	private static UserNameValidation validUserName;
	private static ContactValidatorFactory factory = new ContactValidatorFactory();
	private static UserRepository userFileRepository = new UserFileRepository();
	private static ContactRepository contactFileReposytory = new ContactFileReposytory();

	private static byte readUserChoice(int minChoice, int maxChoice) {
		byte choice = 0;
		do {
			sc = new Scanner(System.in);
			if (sc.hasNextByte()) {
				choice = sc.nextByte();
				if (choice < minChoice | choice > maxChoice) {
					choice = 0;
					System.out.print("Incorrect choice. Try once more: ");
				}
			} else {
				System.out.print("Incorrect choice. Try once more: ");
			}
		} while (choice < minChoice | choice > maxChoice);
		return choice;
	}

	private static String getEnteredStringData() {
		String enteredData = "";
		do {
			sc = new Scanner(System.in);
			if (sc.hasNext() & sc.hasNextLine()) {
				enteredData = sc.nextLine();
				break;
			}
		} while (!sc.hasNext() & !sc.hasNextLine());
		return enteredData;
	}

	private static int getEnteredIntData() {
		int enteredData;
		sc = new Scanner(System.in);
		if (sc.hasNext() & sc.hasNextInt()) {
			enteredData = sc.nextInt();
			return enteredData;
		} else {
			System.out.println("Incorrect value entry!");
		}
		return 0;
	}

	private static boolean userExist(int id) {
		if (id != 0) {
			for (User user : userFileRepository.getAll()) {
				if (user.getUserId() == id) {
					return true;
				}
			}
		}
		System.out.println("\nUser's id not found!");
		return false;
	}

	private static String createNewUser() {
		validUserName = new UserNameValidation();
		String userName = "";
		do {
			userName = getEnteredStringData();
			if (validUserName.isValid(userName)) {
				return userName;
			} else {
				System.out.println("\nIncorrect user name.");
				System.out.println(
						"Rules for entering user name:  consist only of letters,  length at least 3 characters.");
				System.out.print("Try once more: ");
			}
		} while (!validUserName.isValid(userName));
		return userName;
	}

	private static void showAllUsersInContactBook() {
		System.out.println("\n" + "All users in ContactBook:");
		System.out.println("User ID" + "\t\t" + "User name");
		for (User user : userFileRepository.getAll()) {
			System.out.println(user.getUserId() + "\t\t" + user.getUserName());
		}
	}

	private static void showAllContactsInContactBook() {
		System.out.println("\nAll contacts in ContactBook:");
		System.out.println("Contact ID" + "\t" + "User name" + "\t" + "Contact type" + "\t\t" + "Contact value");
		for (Contact contact : contactFileReposytory.getAll()) {
			System.out.println(contact.getContactId() + "\t\t" + userFileRepository.getUserNameById(contact.getUserId())
					+ "\t\t" + contact.getContactType() + "\t\t\t" + contact.getContactValue());
		}
	}

	private static void showAllContactBookEntries() {
		List<String> userContactList = new ArrayList<>();
		for (User user : userFileRepository.getAll()) {
			userContactList.add("\nUser id: " + user.getUserId() + "      " + user.getUserName());
			userContactList.add("#Contacts:");
			for (Contact contact : contactFileReposytory.getAll()) {
				if (user.getUserId() == contact.getUserId()) {
					userContactList.add("Contact id: " + contact.getContactId() + "\t" + contact.getContactType() + "\t"
							+ contact.getContactValue());
				}
			}
		}
		for (String line : userContactList) {
			System.out.println(line);
		}
	}

	private static ContactType createContactType() {
		System.out.println("\nSelect new user's contact for saving: ");
		System.out.println("1.  E-mail.");
		System.out.println("2.  Mobile phone number.");
		System.out.println("3.  Company name.");
		System.out.println("4.  Address.");
		System.out.println("5.  Work phone number.");
		System.out.print("Your choice: ");

		byte choice = readUserChoice(1, ContactType.values().length);
		return ContactType.getByOrderCode(choice);
	}

	private static Contact createContactTypeValue(ContactType type) {
		Contact contact = new Contact();
		String contactTypeData;

		System.out.print("Enter user's " + type + ": ");

		ContactValidator enteredType = factory.getValidator(type);
		do {
			contactTypeData = getEnteredStringData();
			if (enteredType.isValid(contactTypeData)) {
				contact.setContactType(type.toString());
				contact.setContactValue(contactTypeData);
				return contact;
			} else {
				System.out.println("\nIncorrect contact's value.");
				System.out.println(enteredType.getDataEntryRules());
				System.out.print("Try once more: ");
			}
		} while (!enteredType.isValid(contactTypeData));
		return contact;
	}

	private static List<String> createUserContactList(String userName) {
		List<String> userContactList = new ArrayList<>();
		int count = 0;
		for (User user : userFileRepository.getAll()) {
			String userNameInUserList = user.getUserName();
			if (userNameInUserList.equals(userName)) {
				userContactList.add("\n#User name:  " + userNameInUserList);
				userContactList.add("#Contacts:");
				count++;
				for (Contact contact : contactFileReposytory.getAll()) {
					int userIdInContactList = contact.getUserId();
					if (userIdInContactList == user.getUserId()) {
						userContactList.add(contact.getContactType() + "\t\t" + contact.getContactValue());
					}
				}
			}
		}
		if (count == 0)
			System.out.println("\nUser not found!");
		return userContactList;
	}

	private final static void showMainMenu() {
		System.out.println("\n\"Main menu\" options: ");
		System.out.println("1 -  Show contact book entries.");
		System.out.println("2 -  Find user.");
		System.out.println("3 -  Add new user.");
		System.out.println("4 -  Add new user's contact.");
		System.out.println("5 -  Edit user's data.");
		System.out.println("6 -  Delete.");
		System.out.println("7 -  Exit.");
		System.out.print("Select an action: ");
	}

	private static final void showMenuShowContactbookEntries() {
		System.out.println("\n\"Show contact book entries\" options:");
		System.out.println("1 -  Show all users in contact book.");
		System.out.println("2 -  Show all contacts in contact book.");
		System.out.println("3 -  Show all entries in contact book.");
		System.out.println("4 -  Exit.");
		System.out.print("Select: ");
	}

	private static final void showMenuFindUser() {
		System.out.println("\n\"Find user\" options:");
		System.out.println("1 -  Search by user's name.");
		System.out.println("2 -  Search by user's contact data.");
		System.out.println("3 -  Exit.");
		System.out.print("Select: ");
	}

	private static final void showMenuAddNewUser() {
		System.out.println("\n\"Add user\" options:");
		System.out.println("1 -  Enter new user's name.");
		System.out.println("2 -  Exit.");
		System.out.print("Select: ");
	}

	private static final void showMenuAddNewUserData() {
		System.out.println("\n\"Add new user's contact\" options:");
		System.out.println("1 -  Choose user to add new user's contact.");
		System.out.println("2 -  Exit.");
		System.out.print("Select: ");
	}

	private static final void showMenuEditUsersData() {
		System.out.println("\n\"Edit user's data\" options:");
		System.out.println("1 -  Edit user's name.");
		System.out.println("2 -  Edit user's contact data.");
		System.out.println("3 -  Exit.");
		System.out.print("Select: ");
	}

	private static final void showMenuDeleteUsersData() {
		System.out.println("\n\"Delete\" options:");
		System.out.println("1 -  Delete user.");
		System.out.println("2 -  Delete user's contact.");
		System.out.println("3 -  Exit.");
		System.out.print("Select: ");
	}

	public static void main(String[] args) throws IOException {

		byte mainChoice, choice;
		do {
			showMainMenu();
			mainChoice = readUserChoice(1, 7);

			switch (mainChoice) {

			case 1:// Show contact book entries
			{
				showMenuShowContactbookEntries();
				choice = readUserChoice(1, 4);

				switch (choice) {
				case 1:
					showAllUsersInContactBook();
					break;
				case 2:
					showAllContactsInContactBook();
					break;
				case 3:
					showAllContactBookEntries();
					break;
				case 4:
					break;
				}
				break;
			}

			case 2:// Find user
			{
				showMenuFindUser();
				handleFindUserOptions(readUserChoice(1, 3));
				break;
			}

			case 3:// Add new user
			{
				showMenuAddNewUser();
				choice = readUserChoice(1, 2);

				switch (choice) {
				case 1:
					System.out.print("Enter new user name: ");
					User user = new User();
					user.setUserName(createNewUser());
					userFileRepository.userSave(user);
					break;
				case 2:
					break;
				}
				break;
			}

			case 4:// Add new user's contact
			{
				showMenuAddNewUserData();
				choice = readUserChoice(1, 2);

				switch (choice) {
				case 1:
					userFileRepository = new UserFileRepository();
					showAllUsersInContactBook();
					System.out.print("Select user id: ");

					int id = getEnteredIntData();
					if (userExist(id)) {
						Contact contact = createContactTypeValue(createContactType());
						contact.setUserId(id);
						contactFileReposytory.contactSave(contact);
					}
					break;
				case 2:
					break;
				}
				break;
			}

			case 5:// Edit user's data
			{
				showMenuEditUsersData();
				choice = readUserChoice(1, 3);

				switch (choice) {
				case 1:

					showAllUsersInContactBook();
					System.out.print("Select user's id to edit user's name: ");
					int id = getEnteredIntData();

					if (userExist(id)) {
						User user = new User();
						System.out.print("Enter new user name to overwrite: ");
						user.setUserName(createNewUser());
						user.setUserId(id);
						if (userFileRepository.userUpdate(user) != null) {
							System.out.println("\nUser has been updated!");
						}
					}
					break;

				case 2:

					showAllContactsInContactBook();
					System.out.print("Select contact's id to edit: ");

					int contactId = getEnteredIntData();
					boolean result = false;
					Contact updatedContact = new Contact();

					for (Contact contact : contactFileReposytory.getAll()) {
						if (contact.getContactId() == contactId) {
							updatedContact = createContactTypeValue(ContactType.valueOf(contact.getContactType()));
							updatedContact.setContactId(contact.getContactId());
							updatedContact.setUserId(contact.getUserId());
							result = true;
						}
					}
					if (contactFileReposytory.contactUpdate(updatedContact) != null & result == true) {
						System.out.println("\nContact has been updated!");
					}
					if (result == false)
						System.out.println("\nContact's id not found!");
					break;

				case 3:
					break;
				}
				break;
			}

			case 6:// Delete
			{
				showMenuDeleteUsersData();
				choice = readUserChoice(1, 3);

				switch (choice) {
				case 1:
					showAllContactBookEntries();
					System.out.print("Enter user id for deleting user: ");
					int id = getEnteredIntData();
					contactFileReposytory.deleteAllUserContacts(id);
					if (userFileRepository.delete(id)) {
						System.out.println("\nUser has been deleted!");
					} else {
						System.out.println("\nUser's id not found!");
					}

					break;

				case 2:

					showAllContactsInContactBook();
					System.out.print("Enter contact's id for deleting: ");
					int contactId = getEnteredIntData();
					if (contactFileReposytory.deleteContact(contactId)) {
						System.out.println("\nContact has been deleted!");
					} else {
						System.out.println("\nContact's id not found!");
					}
					break;

				case 3:
					break;
				}
				break;
			}

			case 7:
				System.out.println("\n---------- Program completed! ----------");
				break;

			}
		} while (mainChoice != 7);
	}

	private static void handleFindUserOptions(byte choice) {
		switch (choice) {
		case 1: {
			System.out.print("Enter user's name for searching: ");
			for (String line : createUserContactList(getEnteredStringData())) {
				System.out.println(line);
			}
			break;
		}

		case 2: {
			int count = 0;
			System.out.print("Enter contact's value for searching: ");
			String enteredData = getEnteredStringData();
			for (Contact contact : contactFileReposytory.getAll()) {
				if (contact.getContactValue().equals(enteredData)) {
					count++;
					for (User user : userFileRepository.getAll()) {
						if (user.getUserId() == contact.getUserId())
							for (String line : createUserContactList(user.getUserName())) {
								System.out.println(line);
							}
					}
				}
			}
			if (count == 0)
				System.out.println("\nContact not found!");
			break;
		}
		case 3:
			break;
		}
	}
}