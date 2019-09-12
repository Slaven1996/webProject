package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import enumerations.ClassFlight;

public class Flight {
	private String numberOfFlight;
	private Destination start;
	private Destination departure;
	private HashMap<Integer, Reservation> reservations;
	private int ticketPrice;
	private String airplane;
	private int numberOfSeatsFirstClass;
	private int numberOfSeatsBusinessClass;
	private int numberOfSeatsEconomicClass;
	private LocalDateTime dateOfFlight;
	private ClassFlight classFlight;
	
	public Flight(){
		
	}

	public Flight(String numberOfFlight, Destination start, Destination departure, HashMap<Integer, Reservation> reservations,
			int ticketPrice, String airplane, int numberOfSeatsFirstClass, int numberOfSeatsBusinessClass,
			int numberOfSeatsEconomicClass, LocalDateTime dateOfFlight, ClassFlight classFlight) {
		super();
		this.numberOfFlight = numberOfFlight;
		this.start = start;
		this.departure = departure;
		this.reservations = reservations;
		this.ticketPrice = ticketPrice;
		this.airplane = airplane;
		this.numberOfSeatsFirstClass = numberOfSeatsFirstClass;
		this.numberOfSeatsBusinessClass = numberOfSeatsBusinessClass;
		this.numberOfSeatsEconomicClass = numberOfSeatsEconomicClass;
		this.dateOfFlight = dateOfFlight;
		this.classFlight = classFlight;
	}

	public String getNumberOfFlight() {
		return numberOfFlight;
	}

	public void setNumberOfFlight(String numberOfFlight) {
		this.numberOfFlight = numberOfFlight;
	}

	public Destination getStart() {
		return start;
	}

	public void setStart(Destination start) {
		this.start = start;
	}

	public Destination getDeparture() {
		return departure;
	}

	public void setDeparture(Destination departure) {
		this.departure = departure;
	}

	public HashMap<Integer, Reservation>  getReservations() {
		return reservations;
	}

	public void setReservations(HashMap<Integer, Reservation>  reservations) {
		this.reservations = reservations;
	}

	public int getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public String getAirplane() {
		return airplane;
	}

	public void setAirplane(String airplane) {
		this.airplane = airplane;
	}

	public int getNumberOfSeatsFirstClass() {
		return numberOfSeatsFirstClass;
	}

	public void setNumberOfSeatsFirstClass(int numberOfSeatsFirstClass) {
		this.numberOfSeatsFirstClass = numberOfSeatsFirstClass;
	}

	public int getNumberOfSeatsBusinessClass() {
		return numberOfSeatsBusinessClass;
	}

	public void setNumberOfSeatsBusinessClass(int numberOfSeatsBusinessClass) {
		this.numberOfSeatsBusinessClass = numberOfSeatsBusinessClass;
	}

	public int getNumberOfSeatsEconomicClass() {
		return numberOfSeatsEconomicClass;
	}

	public void setNumberOfSeatsEconomicClass(int numberOfSeatsEconomicClass) {
		this.numberOfSeatsEconomicClass = numberOfSeatsEconomicClass;
	}

	public LocalDateTime getDateOfFlight() {
		return dateOfFlight;
	}

	public void setDateOfFlight(LocalDateTime dateOfFlight) {
		this.dateOfFlight = dateOfFlight;
	}

	public ClassFlight getClassFlight() {
		return classFlight;
	}

	public void setClassFlight(ClassFlight classFlight) {
		this.classFlight = classFlight;
	}

	@Override
	public String toString() {
		return "Flight [numberOfFlight=" + numberOfFlight + ", start=" + start + ", departure=" + departure
				 + ", ticketPrice=" + ticketPrice + ", airplane=" + airplane
				+ ", numberOfSeatsFirstClass=" + numberOfSeatsFirstClass + ", numberOfSeatsBusinessClass="
				+ numberOfSeatsBusinessClass + ", numberOfSeatsEconomicClass=" + numberOfSeatsEconomicClass
				+ ", dateOfFlight=" + dateOfFlight + ", classFlight=" + classFlight + "]";
	}
	
	
	
	
}
