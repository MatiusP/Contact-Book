package savedata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.finalwork.contactbook.Contact;

public class ContactFileReposytory implements ContactRepository {
	private static final String CONTACT_FILE_NAME = "ContactBook_ContactFile.csv";
	private static final String TEMP_CONTACT_FILE_NAME = "myTempContactFile.csv";

	public int fileWrite(int userId, String contactType, String contactValue) throws IOException {
		File file = new File(CONTACT_FILE_NAME);

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter out = new FileWriter(CONTACT_FILE_NAME, true);

			try (BufferedWriter writer = new BufferedWriter(out)) {
				int id = IdGenerator.getLastId(CONTACT_FILE_NAME) + 1;
				writer.write(id + "," + userId + "," + contactType + "," + contactValue + "\r\n");
				return id;
			} catch (FileNotFoundException e) {
				System.out.println("Error! " + e.getMessage());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error! " + e.getMessage());
		}
		return 0;
	}

	private Contact parseContact(String line) {
		Contact contact = new Contact();
		int firstIndex = line.indexOf(",");
		int secondIndex = line.indexOf(",", firstIndex + 1);
		int lastIndex = line.lastIndexOf(",");

		contact.setContactId(Integer.parseInt(line.substring(0, firstIndex)));
		contact.setUserId(Integer.parseInt(line.substring((firstIndex + 1), secondIndex)));
		contact.setContactType(line.substring((secondIndex + 1), lastIndex));
		contact.setContactValue(line.substring(lastIndex + 1));

		return contact;
	}

	@Override
	public Contact contactSave(Contact contact) {
		try {
			int id = fileWrite(contact.getUserId(), contact.getContactType(), contact.getContactValue());
			if (id == 0) {
				System.out.println("\nContact hasn't been added!");
				return null;
			}
			contact.setContactId(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nNew contact has been added!");
		return contact;
	}

	@Override
	public Contact contactUpdate(Contact contact) {

		File inputFile = new File(CONTACT_FILE_NAME);
		File tempFile = new File(TEMP_CONTACT_FILE_NAME);

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

			int contactToUpdate = contact.getContactId();
			String currentLine;

			while ((currentLine = reader.readLine()) != null) {
				String trimmedLine = currentLine.trim();
				Integer lineID = Integer.parseInt(trimmedLine.substring(0, trimmedLine.indexOf(",")));
				if (lineID == contactToUpdate) {
					trimmedLine = (contact.getContactId() + "," + contact.getUserId() + "," + contact.getContactType()
							+ "," + contact.getContactValue());
					writer.write(trimmedLine);
					writer.newLine();
					continue;
				}
				writer.write(trimmedLine);
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error! " + e.getMessage());
		}

		File oldFile = new File(CONTACT_FILE_NAME);
		if (oldFile.delete()) {
			tempFile.renameTo(oldFile);
		} else {
			contact = null;
			System.out.println("\nContact hasn't been updated! File is used.");
		}
		return contact;
	}

	@Override
	public List<Contact> getAll() {
		String line;
		List<Contact> contactList = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(CONTACT_FILE_NAME))) {
			while ((line = br.readLine()) != null) {
				contactList.add(parseContact(line));
			}
		} catch (IOException e) {
			System.out.println("Error! " + e.getMessage());
		}
		return contactList;
	}

	@Override
	public List<Contact> getAllUserContacts(int id) {
		String line;
		List<Contact> usersContactList = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(CONTACT_FILE_NAME))) {
			while ((line = br.readLine()) != null) {

				Contact contact = parseContact(line);
				if (contact.getUserId() == id) {
					usersContactList.add(contact);
				}
			}
		} catch (IOException e) {
			System.out.println("Error! " + e.getMessage());
		}
		return usersContactList;
	}

	@Override
	public boolean deleteContact(int contactId) {
		boolean result = false;

		File inputFile = new File(CONTACT_FILE_NAME);
		File tempFile = new File(TEMP_CONTACT_FILE_NAME);

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

			int contactToRemove = contactId;
			String currentLine;

			while ((currentLine = reader.readLine()) != null) {
				String trimmedLine = currentLine.trim();
				Integer lineID = Integer.parseInt(trimmedLine.substring(0, trimmedLine.indexOf(",")));
				if (lineID == contactToRemove) {
					result = true;
					continue;
				}
				writer.write(trimmedLine);
				writer.newLine();
			}

		} catch (IOException e) {
			System.out.println("Error! " + e.getMessage());
		}

		File oldFile = new File(CONTACT_FILE_NAME);
		if (oldFile.delete()) {
			tempFile.renameTo(oldFile);
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public boolean deleteAllUserContacts(int userId) {
		boolean count = false;
		for (Contact contact : getAllUserContacts(userId)) {
			if (deleteContact(contact.getContactId()))
				count = true;
			;
		}
		return count;
	}
}