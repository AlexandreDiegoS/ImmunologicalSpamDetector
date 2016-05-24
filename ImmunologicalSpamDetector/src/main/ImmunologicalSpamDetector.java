package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import census.Census;
import detector.Detector;
import monitor.Monitor;
import util.MapValidEmails;
import util.PreprocessEmail;
import util.file.Email;
import util.file.IOManipulation;
import util.file.ProjectProperties;

public class ImmunologicalSpamDetector {
	static Properties projectProperties = ProjectProperties.getProperties();
	public static void main(String[] args) {
		//Fase de censo, onde serão gerados e avaliados os detectores
		List<Detector> detectorsCandidates = Census.generateDetectors();
		Census.evaluatesDetectors(detectorsCandidates);
		
		//Fase de monitoramento, onde será criado o monitor para que o mesmo
		//possa avaliar se algum email é válido ou não.
		Monitor monitor = new Monitor();
		boolean isValid = monitor.verifyEmail(new File("res/spams/0001.txt"));
		
		System.out.println("Verificação: " + isValid);
	}
	public static void generateBaseOfDetectors(){
		List<Detector> detectorsCandidates = Census.generateDetectors();
		Census.evaluatesDetectors(detectorsCandidates);
	}
	public static List<MapValidEmails> verifyEmailByLocation(String filename){
		Monitor monitor = new Monitor();
		File file = new File(filename);
		List<File> emails = new ArrayList<File>();
		List<MapValidEmails> mapEmails = new ArrayList<MapValidEmails>();
		MapValidEmails emailInformation = null;
		
		if (file.isDirectory()){
			emails = IOManipulation.listFilesInDirectory(filename);
			List<File> validFiles = monitor.verifyEmails(emails);
			for(File f : emails){
				String emailFilename = f.getName();
				String emailBody = PreprocessEmail.clearHeader(Email.readEmailContent(f));
				boolean isValid = validFiles.contains(f);
				emailInformation = new MapValidEmails(emailBody, emailFilename, isValid);
				
				mapEmails.add(emailInformation);
			}
		}
		else{
			String emailFilename = file.getName();
			String emailBody = PreprocessEmail.clearHeader(Email.readEmailContent(file));
			boolean isValid = monitor.verifyEmail(file);
			emailInformation = new MapValidEmails(emailBody, emailFilename, isValid);
			mapEmails.add(emailInformation);
		}
		
		return mapEmails;
	}
}
