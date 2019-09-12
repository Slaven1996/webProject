package services;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

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

import org.apache.tomcat.jni.Local;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import dataManager.DataManager;
import dto.DestinationAddDTO;
import dto.DestinationEditDTO;
import dto.FlightAddDTO;
import dto.FlightAdminSearchDTO;
import dto.FlightEditDTO;
import dto.FlightFilterDTO;
import dto.FlightForSearchView;
import dto.FlightSearchForReservation;
import dto.FlightsForEditDeleteViewDTO;
import dto.FlightsForReservationView;
import dto.FoundFlightsDTO;
import dto.MessageSuccessDTO;
import dto.ReservationFrontendDTO;
import dto.SearchFlightUser;
import enumerations.ClassFlight;
import enumerations.ClassType;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Destination;
import model.Flight;
import model.Reservation;
import model.User;


@Path("/flightService")
public class FlightService {

	@Context
	ServletContext context;
	@Context
	HttpServletRequest request;
	
	
	@GET
	@Path("/getFlights")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<FlightsForEditDeleteViewDTO> getFlights() throws IOException, ParseException {
		HashMap<String, Flight> flights = DataManager.getInstance().getFlights();
		ArrayList<FlightsForEditDeleteViewDTO> flightsForEditDeleteViewDTOs = new ArrayList<FlightsForEditDeleteViewDTO>();
		
		
		for(String k: flights.keySet()){
			Flight f = flights.get(k);
				
			String month = (f.getDateOfFlight().getMonthValue()<=9) ? "0"+f.getDateOfFlight().getMonthValue(): String.valueOf(f.getDateOfFlight().getMonthValue());
			String day = (f.getDateOfFlight().getDayOfMonth()<=9) ? "0"+f.getDateOfFlight().getDayOfMonth(): String.valueOf(f.getDateOfFlight().getDayOfMonth());
			
			
			String hours = (f.getDateOfFlight().getHour()<=9) ? "0"+f.getDateOfFlight().getHour():String.valueOf(f.getDateOfFlight().getHour());
			String minutes = (f.getDateOfFlight().getMinute()<=9) ? "0"+f.getDateOfFlight().getMinute():String.valueOf(f.getDateOfFlight().getMinute());
			
			
			
			String datetime = f.getDateOfFlight().getYear() + "-"+ month+"-" + day +"T" + hours +":" + minutes;
			
			FlightsForEditDeleteViewDTO flightED = new FlightsForEditDeleteViewDTO(f.getNumberOfFlight(),f.getStart().getName(),
					f.getDeparture().getName(),f.getTicketPrice(),f.getAirplane(),f.getNumberOfSeatsFirstClass(),
					f.getNumberOfSeatsBusinessClass(),f.getNumberOfSeatsEconomicClass(),datetime,f.getClassFlight().toString());
			
			
			flightsForEditDeleteViewDTOs.add(flightED);
			
		}
		return flightsForEditDeleteViewDTOs;
	}
	
	
	@GET
	@Path("/getIDsOfFlights")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getIDsOfFlights(){
		HashMap<String, Flight> flights = DataManager.getInstance().getFlights();
		ArrayList<String> ids = new ArrayList<String>(flights.keySet());
		return ids;
	}
	
	@POST
	@Path("/searchFlightsForReservation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<FlightsForReservationView> searchFlightsForReservation(FlightSearchForReservation flightSearchForReservation){
		
		HashMap<String, Flight> flights = DataManager.getInstance().getFlights();
		
		ArrayList<FlightsForReservationView> retFlights = new ArrayList<FlightsForReservationView>();
		
		String[] dmy = flightSearchForReservation.getDate().trim().split("-");
			
		int d = Integer.parseInt(dmy[2]);
		int m = Integer.parseInt(dmy[1]);
		int y = Integer.parseInt(dmy[0]);
		
		
		for(String k: flights.keySet()){
			
			Flight f = flights.get(k);
			
			if(!DataManager.getInstance().getDestinations().get(f.getStart().getName()).isActive()){
				continue;
			}
			
			if(!DataManager.getInstance().getDestinations().get(f.getDeparture().getName()).isActive()){
				continue;
			}
	
			if(!f.getStart().getName().equalsIgnoreCase(flightSearchForReservation.getStart())){
				continue;
			}
			
			if(!f.getDeparture().getName().equalsIgnoreCase(flightSearchForReservation.getDeparture())){
				continue;
			}
		
			if(f.getDateOfFlight().getDayOfMonth() == d && f.getDateOfFlight().getMonthValue() == m && f.getDateOfFlight().getYear() == y ){
				retFlights.add(new FlightsForReservationView(f.getNumberOfFlight(),f.getTicketPrice(),f.getAirplane(),f.getNumberOfSeatsFirstClass(),
						f.getNumberOfSeatsBusinessClass(),f.getNumberOfSeatsEconomicClass(),f.getClassFlight().toString()));
			}
		
		}

		return retFlights;
	}
	
	
	
	@POST
	@Path("/addFlight")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MessageSuccessDTO addFlight(FlightAddDTO flightAddDTO) throws IOException, ParseException{
		HashMap<String, Flight> flights = DataManager.getInstance().getFlights();
		HashMap<String, Destination> destinations = DataManager.getInstance().getDestinations();
		
		if(flights.containsKey(flightAddDTO.getNumber())){
			return new MessageSuccessDTO("Flight with the same number already exists!",false);
		}
		
		
		String star = flightAddDTO.getStart().toLowerCase();
		String depa = flightAddDTO.getDeparture().toLowerCase();
		
		char[] chars = star.toCharArray();
		chars[0] = star.substring(0, 1).toUpperCase().charAt(0);
		star = String.valueOf(chars);
		
		if(!destinations.containsKey(star)){
			return new MessageSuccessDTO("Start destination doens't exist!",false);
		}
		
		if(!destinations.get(star).isActive()){
			return new MessageSuccessDTO("This start destination is no longer active!",false);
		}
		
		chars = depa.toCharArray();
		chars[0] = depa.substring(0, 1).toUpperCase().charAt(0);
		depa = String.valueOf(chars);
		
		if(!destinations.containsKey(depa)){
			return new MessageSuccessDTO("Departure destination doens't exist!",false);
		}
		
		if(!destinations.get(depa).isActive()){
			return new MessageSuccessDTO("This departure destination is no longer active!",false);
		}

		if(star.equalsIgnoreCase(depa)){
			return new MessageSuccessDTO("Start and departure destination must be different!",false);
		}
	
		String[] dateData = flightAddDTO.getDateTime().split("T");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(dateData[0] + " " + dateData[1], formatter);
		
		if(existFlightWithTheSameStartDepartureDateTime(star, depa, dateTime, flights)){
			return new MessageSuccessDTO("Flight with the same start, departure and date and time already exists!",false);
		}
		Flight f = new Flight(flightAddDTO.getNumber(), destinations.get(star), destinations.get(depa), new HashMap<Integer, Reservation>(), flightAddDTO.getPriceTicket(),
				flightAddDTO.getAirplaneModel(), flightAddDTO.getNumberFirstClass(), flightAddDTO.getNumberBusinessClass(),
				flightAddDTO.getNumberEconomicClass(), dateTime, ClassFlight.valueOf(flightAddDTO.getClassFlight()));
		
		DataManager.getInstance().getFlights().put(flightAddDTO.getNumber(), f);
		DataManager.getInstance().writeFlights(context.getRealPath(""));
		return new MessageSuccessDTO("Flight was added :)",true);
	}
	
	
	@PUT
	@Path("/editFlight")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MessageSuccessDTO editFlight(FlightEditDTO flightEditDTO) throws IOException, ParseException{
		HashMap<String, Flight> flights = DataManager.getInstance().getFlights();
		
		Flight f = flights.get(flightEditDTO.getNumber());
		
		
		
		String[] dateData = flightEditDTO.getDateTime().split("T");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(dateData[0] + " " + dateData[1], formatter);
		
		flights.remove(flightEditDTO.getNumber());
		if(existFlightWithTheSameStartDepartureDateTime(f.getStart().getName(), f.getDeparture().getName(), dateTime, flights)){
			DataManager.getInstance().getFlights().put(f.getNumberOfFlight(), f);
			return new MessageSuccessDTO("Flight with the same start, departure and date and time already exists!",false);
		}
		
		int previusFirst = f.getNumberOfSeatsFirstClass();
		f.setNumberOfSeatsFirstClass(flightEditDTO.getNumberFirstClass());
		if(!ReservationService.existsFreeSeats(f, ClassType.FIRST.toString(), 0)){
			f.setNumberOfSeatsFirstClass(previusFirst);
			DataManager.getInstance().getFlights().put(f.getNumberOfFlight(), f);
			return new MessageSuccessDTO("New number of seats in first class disrupts the current number of reservations!",false);
		}
		
		int previusBusiness = f.getNumberOfSeatsBusinessClass();
		f.setNumberOfSeatsBusinessClass(flightEditDTO.getNumberBusinessClass());
		if(!ReservationService.existsFreeSeats(f, ClassType.BUSINESS.toString(), 0)){
			f.setNumberOfSeatsBusinessClass(previusBusiness);
			DataManager.getInstance().getFlights().put(f.getNumberOfFlight(), f);
			return new MessageSuccessDTO("New number of seats in business class disrupts the current number of reservations!",false);
		}
		
		
		int previusEconomic = f.getNumberOfSeatsEconomicClass();
		f.setNumberOfSeatsEconomicClass(flightEditDTO.getNumberEconomicClass());
		if(!ReservationService.existsFreeSeats(f, ClassType.ECONOMIC.toString(), 0)){
			f.setNumberOfSeatsEconomicClass(previusEconomic);
			DataManager.getInstance().getFlights().put(f.getNumberOfFlight(), f);
			return new MessageSuccessDTO("New number of seats in economic class disrupts the current number of reservations!",false);
		}
		
		
		f.setTicketPrice(flightEditDTO.getPriceTicket());
		f.setAirplane(flightEditDTO.getAirplaneModel());
		f.setDateOfFlight(dateTime);
		f.setClassFlight(ClassFlight.valueOf(flightEditDTO.getClassFlight()));
		
		DataManager.getInstance().getFlights().put(f.getNumberOfFlight(), f);
		DataManager.getInstance().writeFlights(context.getRealPath(""));
		return new MessageSuccessDTO("You successfully updated flight :)",true);
	}
	
	
	
	
	public boolean existFlightWithTheSameStartDepartureDateTime(String start,String departure,LocalDateTime l,HashMap<String,Flight> flights){
		for(String k: flights.keySet()){
			Flight f = flights.get(k);
			if(f.getStart().getName().equalsIgnoreCase(start) && f.getDeparture().getName().equalsIgnoreCase(departure)){
				if(l.getDayOfMonth() == f.getDateOfFlight().getDayOfMonth()  && l.getMonthValue() == f.getDateOfFlight().getMonthValue() &&
						l.getYear() == f.getDateOfFlight().getYear() && l.getHour()==f.getDateOfFlight().getHour()&&
						l.getMinute()==f.getDateOfFlight().getMinute()){
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	@POST
	@Path("/searchFlightsForUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<FlightForSearchView> searchFlightsForUser(SearchFlightUser searchFlightUser){
		HashMap<String, Flight> flights = DataManager.getInstance().getFlights();
		
		ArrayList<FlightForSearchView> retFlights = new ArrayList<FlightForSearchView>();
		
		int d = 0;
		int m = 0;
		int y = 0;
		
		if(searchFlightUser.getDate().trim() != ""){
			String[] dmy = searchFlightUser.getDate().trim().split("-");
			
			d = Integer.parseInt(dmy[2]);
			m = Integer.parseInt(dmy[1]);
			y = Integer.parseInt(dmy[0]);
		}
		
		for(String k: flights.keySet()){
			
			Flight f = flights.get(k);
			
			if(!DataManager.getInstance().getDestinations().get(f.getStart().getName()).isActive()){
				continue;
			}
			
			if(!DataManager.getInstance().getDestinations().get(f.getDeparture().getName()).isActive()){
				continue;
			}
			
			
			if(searchFlightUser.isCountry()){
				if(!f.getStart().getCountry().equalsIgnoreCase(searchFlightUser.getFrom()) && !searchFlightUser.getFrom().equals("")){
					continue;
				}
				
				if(!f.getDeparture().getCountry().equalsIgnoreCase(searchFlightUser.getTo()) && !searchFlightUser.getTo().equals("")){
					continue;
				}
			}else{
				if(!f.getStart().getName().equalsIgnoreCase(searchFlightUser.getFrom()) && !searchFlightUser.getFrom().equals("")){
					continue;
				}
				
				if(!f.getDeparture().getName().equalsIgnoreCase(searchFlightUser.getTo()) && !searchFlightUser.getTo().equals("")){
					continue;
				}
			}
			
	

		
			if(!(f.getDateOfFlight().getDayOfMonth() == d && f.getDateOfFlight().getMonthValue() == m && f.getDateOfFlight().getYear() == y) && !searchFlightUser.getDate().equals("") ){
				continue;
			}
			
			retFlights.add(new FlightForSearchView(f.getNumberOfFlight(),f.getStart().getName(),f.getDeparture().getName(),
					f.getDateOfFlight().toString(),f.getTicketPrice(),f.getAirplane(),f.getClassFlight().toString()));
		
		}

		return retFlights;
	}
	
	
	@POST
	@Path("/filterFlights")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<FlightForSearchView> filterFlights(FlightFilterDTO flightFilterDTO){
		HashMap<String, Flight> flights = DataManager.getInstance().getFlights();
		ArrayList<FlightForSearchView> retFlights = new ArrayList<FlightForSearchView>();
		ArrayList<Flight> flightsSort = new ArrayList<Flight>();
		
		for(String k: flights.keySet()){
			
			Flight f = flights.get(k);
			
			if(!DataManager.getInstance().getDestinations().get(f.getStart().getName()).isActive()){
				continue;
			}
			
			if(!DataManager.getInstance().getDestinations().get(f.getDeparture().getName()).isActive()){
				continue;
			}
			
			if(!f.getNumberOfFlight().equals(flightFilterDTO.getNumber()) && !flightFilterDTO.getNumber().equals("")){
				continue;
			}
			
			if(!f.getClassFlight().toString().equals(flightFilterDTO.getClassFlight()) && !flightFilterDTO.getClassFlight().equals("")){
				continue;
			}	
			/*if(!(f.getDateOfFlight().getDayOfMonth() == d && f.getDateOfFlight().getMonthValue() == m && f.getDateOfFlight().getYear() == y) && !searchFlightUser.getDate().equals("") ){
				continue;
			}*/
			
			/*retFlights.add(new FlightForSearchView(f.getNumberOfFlight(),f.getStart().getName(),f.getDeparture().getName(),
					f.getDateOfFlight().toString(),f.getTicketPrice(),f.getAirplane(),f.getClassFlight().toString()));*/
			flightsSort.add(f);
		
		}
		
		

		if(flightFilterDTO.getDate().contains("newest")){
			flightsSort.sort((Flight f1, Flight f2)->f2.getDateOfFlight().compareTo((f1.getDateOfFlight())));
		}
		
		for(int i = 0;i<flightsSort.size();i++){
			Flight fs = flightsSort.get(i);
			retFlights.add(new FlightForSearchView(fs.getNumberOfFlight(),fs.getStart().getName(),fs.getDeparture().getName(),
					fs.getDateOfFlight().toString(),fs.getTicketPrice(),fs.getAirplane(),fs.getClassFlight().toString()));
		}
		

		return retFlights;
	}
	
	
	@POST
	@Path("/adminFlightSearch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<FlightForSearchView> adminFlightSearch(FlightAdminSearchDTO flightAdminSearchDTO){
		HashMap<String, Flight> flights = DataManager.getInstance().getFlights();
		
		ArrayList<FlightForSearchView> retFlights = new ArrayList<FlightForSearchView>();
		
		int d = 0;
		int m = 0;
		int y = 0;
		
		if(flightAdminSearchDTO.getDate().trim() != ""){
			String[] dmy = flightAdminSearchDTO.getDate().trim().split("-");
			
			d = Integer.parseInt(dmy[2]);
			m = Integer.parseInt(dmy[1]);
			y = Integer.parseInt(dmy[0]);
		}
		
		for(String k: flights.keySet()){
			
			Flight f = flights.get(k);
			
			if(!DataManager.getInstance().getDestinations().get(f.getStart().getName()).isActive()){
				continue;
			}
			
			if(!DataManager.getInstance().getDestinations().get(f.getDeparture().getName()).isActive()){
				continue;
			}
			
			if(!f.getNumberOfFlight().equals(flightAdminSearchDTO.getNumber()) && !flightAdminSearchDTO.getNumber().equals("")){
				continue;
			}
			
			if(!f.getStart().getName().equalsIgnoreCase(flightAdminSearchDTO.getBegin()) && !flightAdminSearchDTO.getBegin().equals("")){
				continue;
			}
					
			if(!f.getDeparture().getName().equalsIgnoreCase(flightAdminSearchDTO.getEnd()) && !flightAdminSearchDTO.getEnd().equals("")){
				continue;
			}
		
			
			if(!f.getStart().getCountry().equalsIgnoreCase(flightAdminSearchDTO.getCountry()) 
					&& !f.getDeparture().getCountry().equalsIgnoreCase(flightAdminSearchDTO.getCountry()) && !flightAdminSearchDTO.getCountry().equals("")){
				continue;
			}
			
			
			
			if(!(f.getDateOfFlight().getDayOfMonth() == d && f.getDateOfFlight().getMonthValue() == m && f.getDateOfFlight().getYear() == y) && !flightAdminSearchDTO.getDate().equals("") ){
				continue;
			}
			
			if(!f.getClassFlight().toString().equals(flightAdminSearchDTO.getClassFlight()) && !flightAdminSearchDTO.getClassFlight().equals("")){
				continue;
			}	
			
			
			retFlights.add(new FlightForSearchView(f.getNumberOfFlight(),f.getStart().getName(),f.getDeparture().getName(),
					f.getDateOfFlight().toString(),f.getTicketPrice(),f.getAirplane(),f.getClassFlight().toString()));
		
		}

		return retFlights;
	}
	
	
	
	
	@DELETE
	@Path("/deleteFlight/{number}")
	@Produces(MediaType.APPLICATION_JSON)
	public MessageSuccessDTO cancelReservation(@PathParam("number") String number) throws IOException, ParseException {
		HashMap<String,Flight> flights = DataManager.getInstance().getFlights();
		
		if(flights.get(number).getReservations().size() != 0){
			return new MessageSuccessDTO("This flight has reservations! You cannot delete it!",false);
		}
		
		DataManager.getInstance().getFlights().remove(number);
		DataManager.getInstance().writeFlights(context.getRealPath(""));
		return new MessageSuccessDTO("The flight is deleted.",true);
	}
	
}
