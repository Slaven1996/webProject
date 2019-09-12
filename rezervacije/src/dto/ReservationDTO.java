package dto;

public class ReservationDTO {

	private String flightNumber;
	private String classType;
	private int numberOfPassengers;
	
	public ReservationDTO(){
		
	}

	public ReservationDTO(String flightNumber,String classType, int numberOfPassengers) {
		super();
		this.flightNumber = flightNumber;
		this.classType = classType;
		this.numberOfPassengers = numberOfPassengers;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
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
