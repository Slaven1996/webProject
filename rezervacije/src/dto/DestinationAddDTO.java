package dto;

public class DestinationAddDTO {
	private String name;
	private String country;
	private String nameOfAirport;
	private String code;
	
	public DestinationAddDTO(){
		
	}

	public DestinationAddDTO(String name, String country, String nameOfAirport, String code) {
		super();
		this.name = name;
		this.country = country;
		this.nameOfAirport = nameOfAirport;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
	
	
	
}
