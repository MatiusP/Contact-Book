package by.finalwork.contactbook.savedata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import by.finalwork.contactbook.User;
import by.finalwork.contactbook.savedata.IdGenerator;

public class UserFileRepository implements UserRepository {
	private static final String USER_NAME_FILE = "ContactBook_UserFile.csv";
	private static final String TEMP_USER_NAME_FILE = "myTempFile.csv";

	public int fileWrite(String data) throws IOException {
		File file = new File(USER_NAME_FILE);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter out = new FileWriter(USER_NAME_FILE, true);
			try (BufferedWriter writer = new BufferedWriter(out)) {
				int id = IdGenerator.getLastId(USER_NAME_FILE) + 1;
				writer.write(id + "," + data + "\r\n");
				return id;
			} catch (FileNotFoundException e) {
				System.out.println("Error! " + e.getMessage());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error! " + e.getMessage());
		}
		return 0;
	}

	private static User parseUser(String line) {
		User user = new User();

		user.setUserId(Integer.parseInt(line.substring(0, line.indexOf(","))));
		user.setUserName(line.substring(line.indexOf(",") + 1));

		return user;
	}

	@Override
	public User userSave(User user) {
		try {
			int id = fileWrite(user.getUserName());
			if (id == 0) {
				System.out.println("\nUser hasn't been added!");
				return null;
			}
			user.setUserId(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nNew user has been added!");
		return user;
	}

	@Override
	public User userUpdate(User user) {

		File inputFile = new File(USER_NAME_FILE);
		File tempFile = new File(TEMP_USER_NAME_FILE);
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

			int userToOverwrite = user.getUserId();
			String currentLine;

			while ((currentLine = reader.readLine()) != null) {
				String trimmedLine = currentLine.trim();
				Integer lineID = Integer.parseInt(trimmedLine.substring(0, trimmedLine.indexOf(",")));
				if (lineID == userToOverwrite) {
					trimmedLine = Integer.toString(userToOverwrite) + "," + user.getUserName();
					writer.write(trimmedLine);
					writer.newLine();
					continue;
				}
				writer.write(trimmedLine);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		File oldFile = new File(USER_NAME_FILE);
		if (oldFile.delete()) {
			tempFile.renameTo(oldFile);
		} else {
			user = null;
			System.out.println("\nUser hasn't been updated! File is used.");
		}
		return user;
	}

	@Override
	public List<User> getAll() {
		List<User> userNameList = new ArrayList<>();
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(USER_NAME_FILE))) {
			while ((line = br.readLine()) != null) {
				userNameList.add(parseUser(line));
			}
		} catch (IOException e) {
			System.out.println("\nError! " + e.getMessage());
		}
		return userNameList;
	}

	@Override
	public String getUserNameById(int userId) {
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(USER_NAME_FILE))) {
			while ((line = br.readLine()) != null) {
				if (parseUser(line).getUserId() == userId) {
					return parseUser(line).getUserName();
				}
			}
		} catch (IOException e) {
			System.out.println("Error! " + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean delete(int id) {
		boolean result = false;

		File inputFile = new File(USER_NAME_FILE);
		File tempFile = new File(TEMP_USER_NAME_FILE);
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

			int userToRemove = id;
			String currentLine;

			while ((currentLine = reader.readLine()) != null) {
				String trimmedLine = currentLine.trim();
				int index = trimmedLine.indexOf(",");
				Integer lineID = Integer.parseInt(trimmedLine.substring(0, index));
				if (lineID == userToRemove) {
					result = true;
					continue;
				}
				writer.write(trimmedLine);
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error! " + e.getMessage());
		}

		File oldFile = new File(USER_NAME_FILE);
		if (oldFile.delete()) {
			tempFile.renameTo(oldFile);
		} else {
			result = false;
		}
		return result;
	}
}