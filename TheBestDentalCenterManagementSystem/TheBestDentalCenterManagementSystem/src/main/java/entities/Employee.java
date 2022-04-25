package entities;

public class Employee {
	
	private String id, username, password, fname, mname, lname, role, type, houseNumber, street, city, province, ssn, salary, branch;
	
	public Employee(String id, String fname, String mname, String lname, String role, String type, String houseNumber, String street, 
			String city, String province, String ssn, String salary, String branch) {
		this(id, "", "", fname, mname, lname, role, type, houseNumber, street, city, province, ssn, salary, branch);
	}
	
	public Employee(String id, String username, String password, String fname, String mname, String lname, String role, String type, String houseNumber, String street, 
			String city, String province, String ssn, String salary, String branch) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.mname = mname;
		this.lname = lname;
		this.role = role;
		this.type = type;
		this.houseNumber = houseNumber;
		this.street = street;
		this.city = city;
		this.province = province;
		this.ssn = ssn;
		this.salary = salary;
		this.branch = branch;
	}
	
	public String getId() {return id;}
	
	public String getUsername() { return username; }
	
	public String getPassword() {return password; }
	
	public String getFirstName() {return fname;}
	
	public String getMiddleName() { return mname; }
	
	public String getLastName() {return lname;}
	
	public String getFullName() {
		if (mname != null && mname.length()>0) {
			return fname+" "+mname.charAt(0)+". "+lname;
		}
		return fname+" "+lname;
	}
	
	public String getRole() {return role;}
	
	public String getType() {return type;}
	
	public String getHouseNumber() {return houseNumber;}
	
	public String getStreet() {return street;}
	
	public String getCity() {return city;}
	
	public String getProvince() {return province;}
	
	public String getAddress() {
		return houseNumber+" "+street+", "+city+", "+province;
	}
	
	public String getSSN() {return ssn;}
	
	public String getSalary() {return salary;}
	
	public String getBranch() {return branch;}

}
