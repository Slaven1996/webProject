package dataManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.sun.javafx.collections.MappingChange.Map;
import com.sun.org.apache.xpath.internal.operations.Bool;

import enumerations.ClassFlight;
import enumerations.ClassType;
import enumerations.UserType;
import model.Destination;
import model.Flight;
import model.Reservation;
import model.User;

public class DataManager {
	private static HashMap<String, User> users = new HashMap<String, User>();
	
	private static HashMap<String, Destination> destinations = new HashMap<String, Destination>(); 
	
	private static HashMap<String, Flight> flights = new HashMap<String, Flight>(); 
	
	private static HashMap<Integer, Reservation> reservations = new HashMap<Integer, Reservation>(); 
	
	
	
	private static final DataManager instance = new DataManager();

	public static DataManager getInstance() {
		return instance;
	}

	private DataManager() {
	}
	
	
	
	public static void readAdmins(String path) {
		
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path + "/files/admins.txt"));
			String curLine;
			while ((curLine = br.readLine()) != null) {
				String[] line = curLine.trim().split("\\|");
				users.put(line[0], new User(line[0],line[1],line[2],line[3],line[4],line[5],Boolean.parseBoolean(line[6]),UserType.ADMIN));
			}
			//System.out.println(allUsers.size());
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readUsers(String path) {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path + "/files/users.txt"));
			String curLine;
			while ((curLine = br.readLine()) != null) {
				String[] line = curLine.trim().split("\\|");
				users.put(line[0], new User(line[0],line[1],line[2],line[3],line[4],line[5],Boolean.parseBoolean(line[6]),UserType.CLIENT));
				
			}
			br.close();
			//System.out.println(allUsers.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readDestinations(String path) {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path + "/files/destinations.txt"));
			String curLine;
			while ((curLine = br.readLine()) != null) {
				String[] line = curLine.trim().split("\\|");
				destinations.put(line[0], new Destination(line[0],line[1],line[2],line[3],Boolean.parseBoolean(line[4])));
				//System.out.println(destinations.get(line[0]));
			}
			br.close();
			//System.out.println(allUsers.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readReservations(String path) throws ParseException {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path + "/files/reservations.txt"));
			String curLine;
			while ((curLine = br.readLine()) != null) {
				String[] line = curLine.trim().split("\\|");
				User user = users.get(line[1]);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
				LocalDateTime dateTime = LocalDateTime.parse(line[2], formatter);
				reservations.put(Integer.parseInt(line[0]), new Reservation(Integer.parseInt(line[0]), user, dateTime, ClassType.valueOf(line[3]), Integer.parseInt(line[4])));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readFlights(String path) throws ParseException {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path + "/files/flights.txt"));
			String curLine;
			while ((curLine = br.readLine()) != null) {
				
				String[] line = curLine.trim().split("\\|");
				
				Destination start = destinations.containsKey(line[1].trim())? destinations.get(line[1]): null; 
				Destination departure = destinations.containsKey(line[2].trim())? destinations.get(line[2]): null;
				
				HashMap<Integer, Reservation>  res = new HashMap<Integer, Reservation> ();
				
			
				if(line[3].trim() != "0"){
					if(line[3].trim().contains(";")){
						String[] reservationsLine = line[3].trim().split(";");
						for(String r: reservationsLine){
							if(reservations.containsKey(Integer.parseInt(r))){
								res.put(Integer.parseInt(r),reservations.get(Integer.parseInt(r)));
							}
						}
						
					}else{
						if(reservations.containsKey(Integer.parseInt(line[3]))){
							res.put(Integer.parseInt(line[3]),reservations.get(Integer.parseInt(line[3])));
						}
					}
					
				}
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
				LocalDateTime dateTime = LocalDateTime.parse(line[9], formatter);
				
				Flight flight = new Flight(line[0], start, departure, res, Integer.parseInt(line[4]), line[5],
						Integer.parseInt(line[6]), Integer.parseInt(line[7]),
						Integer.parseInt(line[8]),dateTime, ClassFlight.valueOf(line[10].trim()));
				
				flights.put(line[0], flight);
				
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public static void addNewClient(String path, User user){
		DataManager.users.put(user.getUsername(), user);
		try {
			writeClients(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	
	public static void writeClients(String path) throws IOException{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path+"/files/users.txt")));

		for(String k: users.keySet()){
				if(users.get(k).getUserType() == UserType.CLIENT){
					out.println(users.get(k).getUsername()+"|"+users.get(k).getPassword()+"|"+users.get(k).getName()+"|"+users.get(k).getSurname() + "|"
							+ users.get(k).getPhone()+"|" + users.get(k).getEmail()+"|"+users.get(k).isActive()
							);
				}
		}
		out.close();
	}
	
	public static void writeFlights(String path) throws IOException{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path+"/files/flights.txt")));

		for(String k: flights.keySet()){
			Flight f = flights.get(k);
			
			String reservationLine = "";
			HashMap<Integer, Reservation>  res = f.getReservations();
			
			if(res.size() == 0){
				reservationLine = "0";
			}else if(res.size() == 1){
				int i = res.entrySet().iterator().next().getKey();
				reservationLine += res.get(i).getNumber();
			}else{
				int b = 0;
				for(Integer key: res.keySet()){
					b++;
					if(b != res.size()){
						 reservationLine += res.get(key).getNumber() + ";";
					}else{
						reservationLine += res.get(key).getNumber();
					}
					
					
				}
				
			}
			LocalDateTime d = f.getDateOfFlight();
			String day = (d.getDayOfMonth() <=9) ? "0"+d.getDayOfMonth(): d.getDayOfMonth()+"";
			String month = (d.getMonthValue() <=9) ? "0"+d.getMonthValue(): d.getMonthValue()+"";
			
			String h = (d.getHour()<=9) ? "0"+d.getHour() : d.getHour()+"";
			String min = (d.getMinute()<=9) ? "0"+d.getMinute() : d.getMinute()+"";
			
			
			String dateLine = day+"."+month+"."+d.getYear() + " " + h+":"+min;
			
			
			
			out.println(f.getNumberOfFlight()+"|"+f.getStart().getName()+"|"+f.getDeparture().getName()+
					"|"+reservationLine+"|"+f.getTicketPrice()+"|"+f.getAirplane()+"|"+f.getNumberOfSeatsFirstClass()+"|"+
					f.getNumberOfSeatsBusinessClass()+"|"+f.getNumberOfSeatsEconomicClass()+"|"+ dateLine + "|"
					+ f.getClassFlight().toString());
			
			
		}
		out.close();
	}
	
	
	public static void writeAdmins(String path) throws IOException{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path+"/files/admins.txt")));

		for(String k: users.keySet()){
				if(users.get(k).getUserType() == UserType.ADMIN){
					out.println(users.get(k).getUsername()+"|"+users.get(k).getPassword()+"|"+users.get(k).getName()+"|"+users.get(k).getSurname() + "|"
							+ users.get(k).getPhone()+"|" + users.get(k).getEmail()+"|"+users.get(k).isActive()
							);
				}
		}
		out.close();
	}
	
	public static void writeReservations(String path) throws IOException, ParseException{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path+"/files/reservations.txt")));

		for(Integer k: reservations.keySet()){
			Reservation r = reservations.get(k);
			
			LocalDateTime date = r.getDateAndTime();
			String d = (date.getDayOfMonth()<=9) ? "0"+date.getDayOfMonth() : date.getDayOfMonth()+"";
			String m = (date.getMonthValue()<=9) ? "0"+date.getMonthValue() : date.getMonthValue()+"";
			
			String h = (date.getHour()<=9) ? "0"+date.getHour() : date.getHour()+"";
			String min = (date.getMinute()<=9) ? "0"+date.getMinute() : date.getMinute()+"";
			String s = (date.getSecond()<=9) ? "0"+date.getSecond() : date.getSecond()+"";
			
			String dateLine = d+"."+m+"."+r.getDateAndTime().getYear()+
					" " + h+":"+min+":" + s;
			
			out.println(r.getNumber()+"|"+r.getUser().getUsername()+"|"+ dateLine+"|"+r.getClassType()+"|"+r.getNumberOfPassengers());
				
		}
		out.close();
	}
	
	public static void writeDestinations(String path) throws IOException, ParseException{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path+"/files/destinations.txt")));
		
		for(String k: destinations.keySet()){
			out.println(destinations.get(k).getName()+"|"+destinations.get(k).getCountry()+"|"+destinations.get(k).getNameOfAirport()+
					"|"+destinations.get(k).getCode()+"|"+destinations.get(k).isActive());
				
		}
		out.close();
	}
	
	public void addAndOrderReservations(String fn, Reservation r){
		int b = 0;
		Iterator<Entry<Integer, Reservation>> iter = reservations.entrySet().iterator();
		
		HashMap<Integer, Reservation> newMap = new HashMap<Integer,Reservation>();
		HashMap<Integer, Reservation>  forFlightReservations = flights.get(fn).getReservations();
		
		while(iter.hasNext()){
			b++;
			Reservation reservation = iter.next().getValue();
			int previousId = reservation.getNumber();

			reservation.setNumber(b);
			if(forFlightReservations.containsKey(previousId)){
				forFlightReservations.remove(previousId);
				forFlightReservations.put(b, reservation);
			}
			newMap.put(b, reservation);
			iter.remove();
		}
		b++;
		r.setNumber(b);
		newMap.put(b, r);
		forFlightReservations.put(b, r);
		setReservations(newMap);
		flights.get(fn).setReservations(forFlightReservations);
	}
	
	public void orderReservationsAfterDeleting(String fn){
		int b = 0;
		Iterator<Entry<Integer, Reservation>> iter = reservations.entrySet().iterator();
		
		HashMap<Integer, Reservation> newMap = new HashMap<Integer,Reservation>();
		while(iter.hasNext()){
			b++;
			Reservation reservation = iter.next().getValue();
			int previousId = reservation.getNumber();
				
			reservation.setNumber(b);
			newMap.put(b, reservation);
			iter.remove();
		}
		setReservations(newMap);
		
		
		for(String k: flights.keySet()){
			if(flights.get(k).getReservations().size()>0){
				HashMap<Integer, Reservation>  forFlightReservations = new HashMap<Integer,Reservation>();
				for(Integer i: reservations.keySet()){
					for(Integer j: flights.get(k).getReservations().keySet()){
						String id1 = reservations.get(i).getUser().getUsername() + reservations.get(i).getDateAndTime().toString();
						String id2 = flights.get(k).getReservations().get(j).getUser().getUsername() + flights.get(k).getReservations().get(j).getDateAndTime().toString();
						if(id1.equalsIgnoreCase(id2)){
							Reservation r = reservations.get(i);
							forFlightReservations.put(reservations.get(i).getNumber(), r);
						}
					}
				}
				flights.get(k).setReservations(forFlightReservations);
			}
		}
		
		
	}
	
	
	/*public static void ispisiReservacijeZaLetove(){
		for(String s: flights.keySet()){
			if(flights.get(s).getReservations().size() > 0){
				System.out.println(s);
				for(Integer i: flights.get(s).getReservations().keySet()){
					System.out.println(flights.get(s).getReservations().get(i));
				}
			}
		}
	}
	
	public static void ispisiReservacije(){

			for(Integer i: reservations.keySet()){
				System.out.println(reservations.get(i));
			}

	}*/
	
	
	public static HashMap<String, User> getUsers() {
		return users;
	}

	public static void setUsers(HashMap<String, User> users) {
		DataManager.users = users;
	}

	public static HashMap<String, Destination> getDestinations() {
		return destinations;
	}

	public static void setDestinations(HashMap<String, Destination> destinations) {
		DataManager.destinations = destinations;
	}

	public static HashMap<String, Flight> getFlights() {
		return flights;
	}

	public static void setFlights(HashMap<String, Flight> flights) {
		DataManager.flights = flights;
	}

	public static HashMap<Integer, Reservation> getReservations() {
		return reservations;
	}

	public static void setReservations(HashMap<Integer, Reservation> reservations) {
		DataManager.reservations = reservations;
	}
	
	
	
	
	
	


}
