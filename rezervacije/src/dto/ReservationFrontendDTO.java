package dto;


public class ReservationFrontendDTO {
	private int number;
	private String flightNumber;
	private String localDateTime;
	private String classType;
	private int numberOfPassengers;
	
	public ReservationFrontendDTO(){
		
	}

	public ReservationFrontendDTO(int number, String flightNumber, String localDateTime, String classType,
			int numberOfPassengers) {
		super();
		this.number = number;
		this.flightNumber = flightNumber;
		this.localDateTime = localDateTime;
		this.classType = classType;
		this.numberOfPassengers = numberOfPassengers;
	}

	

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(String localDateTime) {
		this.localDateTime = localDateTime;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public void setNumberOfPassengers(int numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}
	
	
	
}
