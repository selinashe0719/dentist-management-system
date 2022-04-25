package entities;

public class Branch {
	
	private String city, province;
	
	public Branch(String city, String province) {
		this.city = city;
		this.province = province;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getProvince() {
		return province;
	}

}
