package entities;

public class Patient {
	
	private String id, username, password, fname, mname, lname, gender, birthday, houseNumber, street, city, province, ssn;
	
	public Patient(String id, String username, String password, String fname, String mname, String lname, String gender, 
			String birthday, String houseNumber, String street, String city, String province, String ssn) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.gender = gender;
		this.birthday = birthday;
		this.houseNumber = houseNumber;
		this.street = street;
		this.city = city;
		this.province = province;
		this.ssn = ssn;
	}
	
	public Patient(String id, String fname, String mname, String lname, String gender, String birthday, String houseNumber, 
			String street, String city, String province, String ssn) {
		this(id, "", "", fname, mname, lname, gender, birthday, houseNumber, street, city, province, ssn);
	}
	
	public String getId() {return id;}
	
	public String getUsername() {return username;}
	
	public String getPassword() {return password;}
	
	public String getFirstName() {return fname;}
	
	public String getMiddleName() {return "";}
	
	public String getLastName() {return lname;}
	
	public String getFullName() {
		if (mname != null && mname.length() > 0) {
			return fname+" "+mname.charAt(0)+". "+lname;
		}
		return fname+" "+lname;
	}
	
	public String getGender() {return gender;}
	
	public String getBirthday() {return birthday;}
	
	public String getHouseNumber() {return houseNumber;}
	
	public String getStreet() {return street;}
	
	public String getCity() {return city;}
	
	public String getProvince() {return province;}
	
	public String getAddress() {return houseNumber+" "+street+", "+city+", "+province;}
	
	public String getSSN() {return ssn;}

}
