package entities;

public class ProcedureType {
	
	private String code, name, description;
	
	public ProcedureType(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

}
