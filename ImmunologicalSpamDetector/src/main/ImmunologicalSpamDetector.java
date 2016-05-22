package main;

import java.io.File;
import java.util.List;

import census.Census;
import detector.Detector;
import monitor.Monitor;

public class ImmunologicalSpamDetector {
	public static void main(String[] args) {
		//Fase de censo, onde ser�o gerados e avaliados os detectores
		List<Detector> detectorsCandidates = Census.generateDetectors();
		Census.evaluatesDetectors(detectorsCandidates);
		
		//Fase de monitoramento, onde ser� criado o monitor para que o mesmo
		//possa avaliar se algum email � v�lido ou n�o.
		Monitor monitor = new Monitor();
		boolean isValid = monitor.verifyEmail(new File("res/spams/0017.txt"));
		
		System.out.println("Verifica��o: " + isValid);	
	}
}
