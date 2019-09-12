package services;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dataManager.DataManager;
import dto.MessageSuccessDTO;
import dto.ReservationDTO;
import dto.ReservationFrontendDTO;
import dto.UserEditDTO;
import enumerations.ClassFlight;
import enumerations.ClassType;
import enumerations.UserType;
import javafx.scene.chart.PieChart.Data;
import model.Flight;
import model.Reservation;
import model.User;


@Path("/reservationService")
public class ReservationService {

	@Context
	ServletContext context;
	@Context
	HttpServletRequest request;
	
	
	@POST
	@Path("/bookFlight")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MessageSuccessDTO bookFlight(ReservationDTO reservationDTO) throws IOException, ParseException {
		Flight flight = DataManager.getFlights().get(reservationDTO.getFlightNumber());
		HashMap<Integer, Reservation> reservations = flight.getReservations();
		User user = (User) request.getSession().getAttribute("loggedUser");
		
		if(reservations.size() != 0){
			for(Integer i: reservations.keySet()){
				if(reservations.get(i).getUser().getUsername().equals(user.getUsername())){
					return new MessageSuccessDTO("You have already booked this flight!",false);
				}
			}
		}
		
		if(!existsFreeSeats(flight, reservationDTO.getClassType(), reservationDTO.getNumberOfPassengers())){
			return new MessageSuccessDTO("There are no free seats in class that you want!",false);
		}
		

		
		ClassType classType = (ClassType.valueOf(reservationDTO.getClassType()) == ClassType.FIRST) ? ClassType.FIRST :
			(ClassType.valueOf(reservationDTO.getClassType()) == ClassType.BUSINESS) ? ClassType.BUSINESS :
				ClassType.ECONOMIC;
		
		Reservation r = new Reservation(user , LocalDateTime.now(),classType, reservationDTO.getNumberOfPassengers());
		
		DataManager.getInstance().addAndOrderReservations(reservationDTO.getFlightNumber() ,r);
		
		
		DataManager.getInstance().writeReservations(context.getRealPath(""));
		DataManager.getInstance().writeFlights(context.getRealPath(""));
		
		return new MessageSuccessDTO("The flight was successfully booked :)",true);
	}
	
	public static boolean existsFreeSeats(Flight f,String classType, int numberOfPassengers){
		HashMap<Integer, Reservation>  reservations = f.getReservations();
		
		/*if(reservations.size() == 0){
			return true;
		}*/
		
		int n = 0;
		
		for(Integer k: reservations.keySet()){
			if(reservations.get(k).getClassType() == ClassType.valueOf(classType)){
				n += reservations.get(k).getNumberOfPassengers();
			}
		}
		
		if(ClassType.valueOf(classType) == ClassType.FIRST){
			if((n+numberOfPassengers)> f.getNumberOfSeatsFirstClass()){
				return false;
			}
		}else if(ClassType.valueOf(classType) == ClassType.BUSINESS){
			if((n+numberOfPassengers)> f.getNumberOfSeatsBusinessClass()){
				return false;
			}
		}else{
			if((n+numberOfPassengers)> f.getNumberOfSeatsEconomicClass()){
				return false;
			}
		}
		
		return true;
	}
	
	
	@GET
	@Path("/getReservations")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ReservationFrontendDTO> getReservations() throws IOException, ParseException {
		ArrayList<ReservationFrontendDTO> reservationFrontendDTOs = new ArrayList<ReservationFrontendDTO>();
		String username = ((User) request.getSession().getAttribute("loggedUser")).getUsername();
		/*DataManager.ispisiReservacije();
		System.out.println("--------------------------------------");
		DataManager.ispisiReservacijeZaLetove();*/
		
		for(Integer i: DataManager.getInstance().getReservations().keySet()){
			Reservation r = DataManager.getInstance().getReservations().get(i);
			if(r.getUser().getUsername().equals(username)){
				String fn = findFlightNumberFromReservation(r.getNumber());
				ReservationFrontendDTO reservationFrontendDTO = new ReservationFrontendDTO(r.getNumber(), fn,
						r.getDateAndTime().toString(),r.getClassType().toString(),r.getNumberOfPassengers());
				reservationFrontendDTOs.add(reservationFrontendDTO);
			}
		}
		return reservationFrontendDTOs;
	}
	
	@DELETE
	@Path("/cancel/{number}/{flightNumber}")
	public void cancelReservation(@PathParam("number") String number,@PathParam("flightNumber") String flightNumber) throws IOException, ParseException {
		DataManager.getInstance().getReservations().remove(Integer.parseInt(number));
		DataManager.getInstance().getFlights().get(flightNumber).getReservations().remove(Integer.parseInt(number));
		
		DataManager.getInstance().orderReservationsAfterDeleting(flightNumber);
		
		DataManager.getInstance().writeReservations(context.getRealPath(""));
		DataManager.getInstance().writeFlights(context.getRealPath(""));
	}
	
	public String findFlightNumberFromReservation(int number){
		HashMap<String, Flight> flights = DataManager.getInstance().getFlights();
		
		String flightNumber = "";
		
		for(String k: flights.keySet()){
			flightNumber = (flights.get(k).getReservations().containsKey(number)) ? flights.get(k).getNumberOfFlight():null;
			
			if(flightNumber != null){
				//System.out.println(number);
				break;
			}
		}
		return flightNumber;
		
	}
	
	
	
}
