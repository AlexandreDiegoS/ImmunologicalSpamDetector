package util;

public class MapValidEmails {
	private String emailBody;
	private String emailFilename;
	private boolean isValid;
	
	public MapValidEmails(String emailBody, String emailFilename, boolean isValid) {
		this.emailBody = emailBody;
		this.emailFilename = emailFilename;
		this.isValid = isValid;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public String getEmailFilename() {
		return emailFilename;
	}
	
	public boolean isValid() {
		return isValid;
	}
	
}
