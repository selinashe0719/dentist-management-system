package entities;

public class Record {
	
	private String type, medications, symptoms, tooth, comments, employeeName;
	
	public Record(String type, String medications, String symptoms, String tooth, String comments, String employeeName) {
		this.type = type;
		this.medications = medications;
		this.symptoms = symptoms;
		this.tooth = tooth;
		this.comments = comments;
		this.employeeName = employeeName;
	}
	
	public String getType() {
		return type;
	}
	
	public String getMedications() {
		return medications;
	}
	
	public String getSymptoms() {
		return symptoms;
	}
	
	public String getTooth() {
		return tooth;
	}
	
	public String getComments() {
		return comments;
	}
	
	public String getEmployee() {
		return employeeName;
	}

}
