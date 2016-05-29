package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import census.Census;
import detector.Detector;
import monitor.Monitor;
import util.Email;
import util.PreprocessEmail;
import util.file.IOManipulation;

public class ImmunologicalSpamDetector {
	
	public static void main(String[] args) {
		Census.generateSpamDictionary();
		System.out.println("fim");
	}
	
	public static void generateBaseOfDetectors(){
		List<Detector> detectorsCandidates = Census.generateDetectors();
		Census.evaluatesDetectors(detectorsCandidates);
	}
	
	public static List<Email> verifyEmailByLocation(String filename){
		Monitor monitor = new Monitor();
		File file = new File(filename);
		List<File> emailsFilesList = new ArrayList<File>();
		List<Email> emailsList = new ArrayList<Email>();
		Email email = null;
		
		if (file.isDirectory()){
			emailsFilesList = IOManipulation.listFilesInDirectory(filename);
			List<File> validFiles = monitor.verifyEmails(emailsFilesList);
			for(File emailFile : emailsFilesList){
				String emailFilename = emailFile.getName();
				String emailBody = PreprocessEmail.clearHeader(IOManipulation.readEmailContent(emailFile));
				boolean isValid = validFiles.contains(emailFile);
				email = new Email(emailBody, emailFilename, isValid);
				
				emailsList.add(email);
			}
		}else{
			String emailFilename = file.getName();
			String emailBody = PreprocessEmail.clearHeader(IOManipulation.readEmailContent(file));
			boolean isValid = monitor.verifyEmail(file);
			email = new Email(emailBody, emailFilename, isValid);
			emailsList.add(email);
		}
		
		return emailsList;
	}
	
}
