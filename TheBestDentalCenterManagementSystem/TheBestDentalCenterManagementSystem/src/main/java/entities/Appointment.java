package entities;

public class Appointment {
	
	private String id;
	private String date;
	private String starttime;
	private String endtime;
	private String dentistId;
	private String dentistName;
	private String patientId;
	private String patientName;
	private String type;
	private String status;
	private String room;
	
	public Appointment(String id, String date, String starttime, String endtime, String dentistId, String dentistName, String patientId, String patientName, String room, String type, String status) {
		this.id = id;
		this.date = date;
		this.starttime = starttime;
		this.endtime = endtime;
		this.dentistId = dentistId;
		this.dentistName = dentistName;
		this.patientId = patientId;
		this.patientName = patientName;
		this.room = room;
		this.type = type;
		this.status = status;
	}
	
	public String getId() {
		return id;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getStartTime() {
		return starttime.substring(0,5);
	}
	
	public String getEndTime() {
		return endtime.substring(0,5);
	}
	
	public String getDentistId() {
		return dentistId;
	}
	
	public String getDentistName() {
		return dentistName;
	}
	
	public String getPatientId() {
		return patientId;
	}
	
	public String getPatientName() {
		return patientName;
	}
	
	public String getRoom() {
		return room;
	}
	
	public String getType() {
		return type;
	}
	
	public String getStatus() {
		return status;
	}

}
