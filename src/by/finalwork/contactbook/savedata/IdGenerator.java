package by.finalwork.contactbook.savedata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class IdGenerator {

	public static int getLastId(String fileName) throws FileNotFoundException, IOException {
		String trimmedLine = "";
		String currentLine;
		int id = 0;

		File inputFile = new File(fileName);
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {

			while ((currentLine = reader.readLine()) != null) {
				trimmedLine = currentLine.trim();
			}
			if (trimmedLine.equals("")) {
				return id;
			} else {
				int index = trimmedLine.indexOf(",");
				id = Integer.parseInt(trimmedLine.substring(0, index));
			}
		} catch (IOException e) {
			System.out.println("Error! " + e.getMessage());
		}
		return id;
	}
}