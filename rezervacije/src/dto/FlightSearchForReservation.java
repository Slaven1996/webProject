package dto;

public class FlightSearchForReservation {
	private String start;
	private String departure;
	private String date;
	
	public FlightSearchForReservation(){
		
	}

	public FlightSearchForReservation(String start, String departure, String date) {
		super();
		this.start = start;
		this.departure = departure;
		this.date = date;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
