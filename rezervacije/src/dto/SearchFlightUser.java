package dto;

public class SearchFlightUser {
	private String from;
	private String to;
	private String date;
	private boolean country;
	
	
	public SearchFlightUser(){
		
	}


	public SearchFlightUser(String from, String to, String date, boolean country) {
		super();
		this.from = from;
		this.to = to;
		this.date = date;
		this.country = country;
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public boolean isCountry() {
		return country;
	}


	public void setCountry(boolean country) {
		this.country = country;
	}


	@Override
	public String toString() {
		return "SearchFlightAdmin [from=" + from + ", to=" + to + ", date=" + date + ", country=" + country + "]";
	}
	
	
	
	
}
