package services;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dataManager.DataManager;
import dto.DestinationAddDTO;
import dto.DestinationEditDTO;
import dto.FlightSearchForReservation;
import dto.FlightsForReservationView;
import dto.MessageSuccessDTO;
import enumerations.UserType;
import model.Destination;
import model.Flight;
import model.User;

@Path("/destinationService")
public class DestinationService {
	
	@Context
	ServletContext context;
	@Context
	HttpServletRequest request;
	
	
	
	@GET
	@Path("/getDestinations")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Destination> getDestinations(){
		HashMap<String, Destination> destinations = DataManager.getInstance().getDestinations();
		ArrayList<Destination> dest = new ArrayList<>();
		
		for(String k: destinations.keySet()){
			dest.add(destinations.get(k));
		}
		return dest;
	}
	
	
	@GET
	@Path("/getNamesOfDestinations")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getNamesOfDestinations(){
		HashMap<String, Destination> destinations = DataManager.getInstance().getDestinations();
		ArrayList<String> names = new ArrayList<String>(destinations.keySet());
		return names;
	}
	
	
	
	@POST
	@Path("/addDestination")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MessageSuccessDTO addDestination(DestinationAddDTO destinationAddDTO) throws IOException, ParseException{
		HashMap<String, Destination> destinations = DataManager.getInstance().getDestinations();
		
		
		String name = destinationAddDTO.getName().toLowerCase();
		String code = destinationAddDTO.getCode().toUpperCase();
		String country = destinationAddDTO.getCountry().toLowerCase();
		String airport = destinationAddDTO.getNameOfAirport();
		
		char[] chars = name.toCharArray();
		
		chars[0] = name.substring(0, 1).toUpperCase().charAt(0);
		name = String.valueOf(chars);
		
		chars = country.toCharArray();
		chars[0] = country.substring(0, 1).toUpperCase().charAt(0);
		country = String.valueOf(chars);
		
		if(destinations.containsKey(name)){
			return new MessageSuccessDTO("Destination already exists!",false);
		}
		
		if(nameOfAirportAlreadyExists(airport, destinations)){
			return new MessageSuccessDTO("Name of airport already exists!",false);
		}
		
		if(codeAlreadyExists(code, destinations)){
			return new MessageSuccessDTO("Code of airport already exists!",false);
		}
		
		
		DataManager.getInstance().getDestinations().put(name, new Destination(name,country,airport, code));
		
		DataManager.getInstance().writeDestinations(context.getRealPath(""));
		return new MessageSuccessDTO("You successfully added destination :)",true);
	}
	
	@PUT
	@Path("/editDestination")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MessageSuccessDTO editDestination(DestinationEditDTO destinationEditDTO) throws IOException, ParseException{
		HashMap<String, Destination> destinations = DataManager.getInstance().getDestinations();
		Destination d = destinations.get(destinationEditDTO.getName());
		
		if(!destinationEditDTO.getNameOfAirport().equalsIgnoreCase(d.getNameOfAirport())){
			if(nameOfAirportAlreadyExists(destinationEditDTO.getNameOfAirport(), destinations)){
				return new MessageSuccessDTO("Name of airport already exists!",false);
			}
		}
			
		if(!destinationEditDTO.getCode().equalsIgnoreCase(d.getCode())){
			if(codeAlreadyExists(destinationEditDTO.getCode(), destinations)){
				return new MessageSuccessDTO("Code of airport already exists!",false);
			}
			
		}
		
		d.setNameOfAirport(destinationEditDTO.getNameOfAirport());
		d.setCode(destinationEditDTO.getCode().toUpperCase());
		DataManager.getInstance().writeDestinations(context.getRealPath(""));
		return new MessageSuccessDTO("You successfully updated destination :)",true);
	}
	
	
	
	@PUT
	@Path("/archiveDestination/{name}")
	public void archiveDestination(@PathParam("name") String name) throws IOException, ParseException {
		Destination d = DataManager.getDestinations().get(name);
		d.setActive(!d.isActive());
		DataManager.getInstance().writeDestinations(context.getRealPath(""));
	}
	
	
	public boolean nameOfAirportAlreadyExists(String airport, HashMap<String, Destination> destinations){
	
		for(String k: destinations.keySet()){
			if(destinations.get(k).getNameOfAirport().equalsIgnoreCase(airport)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean codeAlreadyExists(String code, HashMap<String, Destination> destinations){
		
		for(String k: destinations.keySet()){
			if(destinations.get(k).getCode().equalsIgnoreCase(code)){
				return true;
			}
		}
		
		return false;
	}

}
