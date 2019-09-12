package dto;

public class FlightEditDTO {
	private String number;
	private int priceTicket;
	private String airplaneModel;
	private int numberFirstClass;
	private int numberBusinessClass;
	private int numberEconomicClass;
	private String dateTime;
	private String classFlight;
	
	public FlightEditDTO(){
		
	}

	public FlightEditDTO(String number, int priceTicket, String airplaneModel, int numberFirstClass,
			int numberBusinessClass, int numberEconomicClass, String dateTime, String classFlight) {
		super();
		this.number = number;
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
		return "FlightEditDTO [number=" + number + ", priceTicket=" + priceTicket + ", airplaneModel=" + airplaneModel
				+ ", numberFirstClass=" + numberFirstClass + ", numberBusinessClass=" + numberBusinessClass
				+ ", numberEconomicClass=" + numberEconomicClass + ", dateTime=" + dateTime + ", classFlight="
				+ classFlight + "]";
	}
	
	
}
