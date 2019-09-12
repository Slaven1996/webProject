package dto;

public class FlightFilterDTO {
	private String number;
	private String date;
	private String classFlight;
	
	public FlightFilterDTO(){
		
	}

	public FlightFilterDTO(String number, String date, String classFlight) {
		super();
		this.number = number;
		this.date = date;
		this.classFlight = classFlight;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getClassFlight() {
		return classFlight;
	}

	public void setClassFlight(String classFlight) {
		this.classFlight = classFlight;
	}

	@Override
	public String toString() {
		return "FlightFilterDTO [number=" + number + ", dateTime=" + date + ", classFlight=" + classFlight + "]";
	}
	
	
}
