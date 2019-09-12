package dto;

public class FlightsForReservationView {
	private String number;
	private int price;
	private String airplane;
	private int fc;
	private int bi;
	private int ec;
	private String classOfFlight;

	
	public FlightsForReservationView(){
		
	}



	public FlightsForReservationView(String number, int price, String airplane, int fc, int bi, int ec,
			String classOfFlight) {
		super();
		this.number = number;
		this.price = price;
		this.airplane = airplane;
		this.fc = fc;
		this.bi = bi;
		this.ec = ec;
		this.classOfFlight = classOfFlight;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public int getFc() {
		return fc;
	}

	public void setFc(int fc) {
		this.fc = fc;
	}

	public int getBi() {
		return bi;
	}

	public void setBi(int bi) {
		this.bi = bi;
	}

	public int getEc() {
		return ec;
	}

	public void setEc(int ec) {
		this.ec = ec;
	}

	public String getClassOfFlight() {
		return classOfFlight;
	}

	public void setClassOfFlight(String classOfFlight) {
		this.classOfFlight = classOfFlight;
	}
	
}
