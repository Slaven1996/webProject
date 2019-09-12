package model;

public class Destination {
	private String name;
	private String country;
	private String nameOfAirport;
	private String code;
	private boolean active;
	
	public Destination(){
		
	}

	public Destination(String name, String country, String nameOfAirport, String code, boolean active) {
		super();
		this.name = name;
		this.country = country;
		this.nameOfAirport = nameOfAirport;
		this.code = code;
		this.active = active;
	}
	
	

	public Destination(String name, String country, String nameOfAirport, String code) {
		super();
		this.name = name;
		this.country = country;
		this.nameOfAirport = nameOfAirport;
		this.code = code;
		this.active = true;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Destination [name=" + name + ", country=" + country + ", nameOfAirport=" + nameOfAirport + ", code="
				+ code + ", active=" + active + "]";
	}
	
	
	
}
