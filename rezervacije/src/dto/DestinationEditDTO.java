package dto;

public class DestinationEditDTO {
	private String name;
	private String nameOfAirport;
	private String code;
	
	public DestinationEditDTO(){
		
	}

	public DestinationEditDTO(String name, String nameOfAirport, String code) {
		super();
		this.name = name;
		this.nameOfAirport = nameOfAirport;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameOfAirport() {
		return nameOfAirport;
	}

	public void setNameOfAirport(String nameOfAirport) {
		this.nameOfAirport = nameOfAirport;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "DestinationEditDTO [name=" + name + ", nameOfAirport=" + nameOfAirport + ", code=" + code + "]";
	}
	
	
	
}
