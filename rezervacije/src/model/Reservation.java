package model;

import java.time.LocalDateTime;
import java.util.Date;

import enumerations.ClassType;

public class Reservation {
	private int number;
	private User user;
	private LocalDateTime dateAndTime;
	private ClassType classType;
	private int numberOfPassengers;
	
	public Reservation(){
		
	}

	public Reservation(int number, User user, LocalDateTime dateAndTime, ClassType classType, int numberOfPassengers) {
		super();
		this.number = number;
		this.user = user;
		this.dateAndTime = dateAndTime;
		this.classType = classType;
		this.numberOfPassengers = numberOfPassengers;
	}
	

	public Reservation(User user, LocalDateTime dateAndTime, ClassType classType, int numberOfPassengers) {
		super();
		this.user = user;
		this.dateAndTime = dateAndTime;
		this.classType = classType;
		this.numberOfPassengers = numberOfPassengers;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(LocalDateTime dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public ClassType getClassType() {
		return classType;
	}

	public void setClassType(ClassType classType) {
		this.classType = classType;
	}

	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public void setNumberOfPassengers(int numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}

	@Override
	public String toString() {
		return "Reservation [number=" + number + ", user=" + user + ", dateAndTime=" + dateAndTime + ", classType="
				+ classType + ", numberOfPassengers=" + numberOfPassengers + "]";
	}
	
	
	
}
