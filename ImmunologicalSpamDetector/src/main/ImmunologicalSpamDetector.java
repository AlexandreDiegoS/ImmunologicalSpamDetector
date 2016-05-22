package main;

import java.io.File;
import java.util.List;

import census.Census;
import detector.Detector;
import monitor.Monitor;

public class ImmunologicalSpamDetector {
	public static void main(String[] args) {
		//Fase de censo, onde serão gerados e avaliados os detectores
		List<Detector> detectorsCandidates = Census.generateDetectors();
		Census.evaluatesDetectors(detectorsCandidates);
		
		//Fase de monitoramento, onde será criado o monitor para que o mesmo
		//possa avaliar se algum email é válido ou não.
		Monitor monitor = new Monitor();
		boolean isValid = monitor.verifyEmail(new File("res/spams/0017.txt"));
		
		System.out.println("Verificação: " + isValid);	
	}
}
