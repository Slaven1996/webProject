package dto;

public class FlightAddDTO {
	private String number;
	private String start;
	private String departure;
	private int priceTicket;
	private String airplaneModel;
	private int numberFirstClass;
	private int numberBusinessClass;
	private int numberEconomicClass;
	private String dateTime;
	private String classFlight;
	
	public FlightAddDTO(){
		
	}

	public FlightAddDTO(String number, String start, String departure, int priceTicket, String airplaneModel,
			int numberFirstClass, int numberBusinessClass, int numberEconomicClass, String dateTime,
			String classFlight) {
		super();
		this.number = number;
		this.start = start;
		this.departure = departure;
		this.priceTicket = priceTicket;
		this.airplaneModel = airplaneModel;
		this.numberFirstClass = numberFirstClass;
		this.numberBusinessClass = numberBusinessClass;
		this.numberEconomicClass = numberEconomicClass;
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

	public int getPriceTicket() {
		return priceTicket;
	}

	public void setPriceTicket(int priceTicket) {
		this.priceTicket = priceTicket;
	}

	public String getAirplaneModel() {
		return airplaneModel;
	}

	public void setAirplaneModel(String airplaneModel) {
		this.airplaneModel = airplaneModel;
	}

	public int getNumberFirstClass() {
		return numberFirstClass;
	}

	public void setNumberFirstClass(int numberFirstClass) {
		this.numberFirstClass = numberFirstClass;
	}

	public int getNumberBusinessClass() {
		return numberBusinessClass;
	}

	public void setNumberBusinessClass(int numberBusinessClass) {
		this.numberBusinessClass = numberBusinessClass;
	}

	public int getNumberEconomicClass() {
		return numberEconomicClass;
	}

	public void setNumberEconomicClass(int numberEconomicClass) {
		this.numberEconomicClass = numberEconomicClass;
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
		return "FlightAddDTO [number=" + number + ", start=" + start + ", departure=" + departure + ", priceTicket="
				+ priceTicket + ", airplaneModel=" + airplaneModel + ", numberFirstClass=" + numberFirstClass
				+ ", numberBusinessClass=" + numberBusinessClass + ", numberEconomicClass=" + numberEconomicClass
				+ ", dateTime=" + dateTime + ", classFlight=" + classFlight + "]";
	}
	
	
	
}
