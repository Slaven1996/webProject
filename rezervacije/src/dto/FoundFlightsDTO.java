package dto;

import java.util.ArrayList;

public class FoundFlightsDTO {
	private String message;
	private ArrayList<FlightsForReservationView> flights;
	
	public FoundFlightsDTO(){
		
	}

	public FoundFlightsDTO(String message, ArrayList<FlightsForReservationView> flights) {
		super();
		this.message = message;
		this.flights = flights;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<FlightsForReservationView> getflights() {
		return flights;
	}

	public void setFlightsForReservationViews(ArrayList<FlightsForReservationView> flights) {
		this.flights = flights;
	}
	
	
}
