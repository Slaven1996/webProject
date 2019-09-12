package dto;

public class FlightAdminSearchDTO {
	private String number;
	private String begin;
	private String end;
	private String country;
	private String date;
	private String classFlight;
	
	public FlightAdminSearchDTO(){
		
	}

	public FlightAdminSearchDTO(String number, String begin, String end, String country, String date,
			String classFlight) {
		super();
		this.number = number;
		this.begin = begin;
		this.end = end;
		this.country = country;
		this.date = date;
		this.classFlight = classFlight;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
		return "FlightAdminSearchDTO [number=" + number + ", begin=" + begin + ", end=" + end + ", country=" + country
				+ ", date=" + date + ", classFlight=" + classFlight + "]";
	}
	
	
	
}
