package main;

import java.io.File;
import java.util.List;
import java.util.Properties;

import util.PreprocessEmail;
import util.file.Email;
import util.file.IOManipulation;
import util.file.ProjectProperties;

public class ImmunologicalSpamDetector {
	static Properties projectProperties = ProjectProperties.getProperties();
	public static void main(String[] args) {
		List<File> emails = new IOManipulation().
				listFilesInDirectory(projectProperties.getProperty("base"));
		String email = new Email().getEmail(emails.get(0));
		System.out.println(PreprocessEmail.
				clearEmail(PreprocessEmail.clearHeader(email)));
	}
}
