package dto;

public class FlightForSearchView {
	private String number;
	private String start;
	private String departure;
	private String date;
	private int price;
	private String airplane;
	private String flightClass;
	
	public FlightForSearchView(){
		
	}

	public FlightForSearchView(String number, String start, String departure, String date, int price, String airplane,
			String flightClass) {
		super();
		this.number = number;
		this.start = start;
		this.departure = departure;
		this.date = date;
		this.price = price;
		this.airplane = airplane;
		this.flightClass = flightClass;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAirplane() {
		return airplane;
	}

	public void setAirplane(String airplane) {
		this.airplane = airplane;
	}

	public String getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}

	@Override
	public String toString() {
		return "FlightForSearchView [number=" + number + ", start=" + start + ", departure=" + departure + ", date="
				+ date + ", price=" + price + ", airplane=" + airplane + ", flightClass=" + flightClass + "]";
	}
	
	


	
}
