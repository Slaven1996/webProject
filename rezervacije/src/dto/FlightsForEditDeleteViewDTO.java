package dto;

public class FlightsForEditDeleteViewDTO {
	private String number;
	private String start;
	private String departure;
	private int price;
	private String airplane;
	private int numberFirst;
	private int numberBussiness;
	private int numberEconomic;
	private String dateTime;
	private String classFlight;
	
	public FlightsForEditDeleteViewDTO(){
		
	}

	public FlightsForEditDeleteViewDTO(String number, String start, String departure, int price, String airplane,
			int numberFirst, int numberBussiness, int numberEconomic, String dateTime, String classFlight) {
		super();
		this.number = number;
		this.start = start;
		this.departure = departure;
		this.price = price;
		this.airplane = airplane;
		this.numberFirst = numberFirst;
		this.numberBussiness = numberBussiness;
		this.numberEconomic = numberEconomic;
		this.dateTime = dateTime;
		this.classFlight = classFlight;
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

	public int getNumberFirst() {
		return numberFirst;
	}

	public void setNumberFirst(int numberFirst) {
		this.numberFirst = numberFirst;
	}

	public int getNumberBussiness() {
		return numberBussiness;
	}

	public void setNumberBussiness(int numberBussiness) {
		this.numberBussiness = numberBussiness;
	}

	public int getNumberEconomic() {
		return numberEconomic;
	}

	public void setNumberEconomic(int numberEconomic) {
		this.numberEconomic = numberEconomic;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getClassFlight() {
		return classFlight;
	}

	public void setClassFlight(String classFlight) {
		this.classFlight = classFlight;
	}

	@Override
	public String toString() {
		return "FlightsForEditDeleteViewDTO [number=" + number + ", start=" + start + ", departure=" + departure
				+ ", price=" + price + ", airplane=" + airplane + ", numberFirst=" + numberFirst + ", numberBussiness="
				+ numberBussiness + ", numberEconomic=" + numberEconomic + ", dateTime=" + dateTime + ", classFlight="
				+ classFlight + "]";
	}
	
	
	
	
}
