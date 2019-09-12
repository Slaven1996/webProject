package services;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import dto.MessageSuccessDTO;
import dto.UserBlockDTO;
import dto.UserDTO;
import dto.UserEditDTO;
import dto.UserRegisterDTO;
import enumerations.UserType;
import model.Reservation;
import model.User;

@Path("userService")
public class UserService {
	
	@Context
	ServletContext context;
	@Context
	HttpServletRequest request;
	
	@GET
	@Path("/readData")
	public void readData() throws IOException, ParseException {
		DataManager.getInstance().readAdmins(context.getRealPath(""));
		DataManager.getInstance().readUsers(context.getRealPath(""));
		DataManager.getInstance().readDestinations(context.getRealPath(""));
		DataManager.getInstance().readReservations(context.getRealPath(""));
		DataManager.getInstance().readFlights(context.getRealPath(""));
	
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User checkLogin(UserDTO userDTO) {
		User user = null;
		HashMap<String,User> users = DataManager.getInstance().getUsers();

		//users = (clients.containsKey(userDTO.getUsername()))? clients: (admins.containsKey(userDTO.getUsername()))? admins: null; 
		
		
		if(users != null && users.containsKey(userDTO.getUsername())){
			if(users.get(userDTO.getUsername()).getPassword().equals(userDTO.getPassword())){
				user = users.get(userDTO.getUsername());
				if(user.isActive()){
					request.getSession().setAttribute("loggedUser", user);
				}
			
			}
		}
		
		return user;
	}
	
	@GET
	@Path("/checkLoggedUser")
	@Produces(MediaType.APPLICATION_JSON)
	public User checkLoggedUser(){
		User user = null;
		user = (User) request.getSession().getAttribute("loggedUser");
		return user;
	}
	
	@GET
	@Path("/logout")
	public void logOut() {
		request.getSession().invalidate();
	}
	
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MessageSuccessDTO registerUser(UserRegisterDTO userRegisterDTO) throws IOException {
		HashMap<String,User> users = DataManager.getInstance().getUsers();
		User client = users.get(userRegisterDTO.getUsername());
		
		System.out.println(userRegisterDTO);
		
		if(client != null){
			return new MessageSuccessDTO("User with this username already exists in database!", false);
		}
		
		
		if(emailExists(userRegisterDTO.getEmail())){
			return new MessageSuccessDTO("The email you entered is already being used!", false);
		}
		
	
		User u = new User(userRegisterDTO);
		HttpSession session = request.getSession();
		session.setAttribute("loggedUser", u);
		DataManager.getInstance().addNewClient(context.getRealPath(""), u);
		return new MessageSuccessDTO("You successfully created an account :)", true);
		
	}
	
	
	@GET
	@Path("/clients")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getClients(){
		HashMap<String, User> users = DataManager.getInstance().getUsers();
		ArrayList<User> clients = new ArrayList<>();
		
		for(String k: users.keySet()){
				if(users.get(k).getUserType() == UserType.CLIENT){
					clients.add(users.get(k));
				}
		}
		return clients;
	}
	
	
	@PUT
	@Path("/blockClient/{username}")
	public void blockClient(@PathParam("username") String username) throws IOException {
		User client = DataManager.getInstance().getUsers().get(username);
		client.setActive(!client.isActive());
		DataManager.writeClients(context.getRealPath(""));
	}
	
	
	@PUT
	@Path("/editUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MessageSuccessDTO editUser(UserEditDTO userEditDTO) throws IOException, ParseException {
		User user = checkLoggedUser();
		String currentUsername = user.getUsername();
		HashMap<String, User> users = DataManager.getInstance().getUsers();

		users.remove(currentUsername);
		
		

		
		for(String k: users.keySet()){       
			if(users.get(k).getUsername().equals(userEditDTO.getUsername())){
				return new MessageSuccessDTO("Username already exists!!!",false);
			}
			if(users.get(k).getEmail().equals(userEditDTO.getEmail())){
				return new MessageSuccessDTO("Email already exists!!!",false);
			}
		}
		
		user.setUsername(userEditDTO.getUsername());
		user.setPassword(userEditDTO.getPassword());
		user.setName(userEditDTO.getName());
		user.setSurname(userEditDTO.getSurname());
		user.setPhone(userEditDTO.getPhone());
		user.setEmail(userEditDTO.getEmail());
		users.put(user.getUsername(), user);
		
		if(userEditDTO.isAdmin()){
			System.out.println("admini");
			DataManager.writeAdmins(context.getRealPath(""));
			
		}else{
			System.out.println("korisnici");
			if(user.getUserType() == UserType.CLIENT){
				HashMap<Integer, Reservation> reservations = DataManager.getInstance().getReservations();
				for(Integer i: reservations.keySet()){
					if(reservations.get(i).getUser().getUsername().equals(currentUsername)){
						reservations.get(i).setUser(user);
					}
				}
				DataManager.writeReservations(context.getRealPath(""));
			}
			DataManager.writeClients(context.getRealPath(""));
		}
		
		return new MessageSuccessDTO("Data was successfully changed :)",true);
	
	}
	
	public boolean emailExists(String email) {
		HashMap<String, User> users = DataManager.getInstance().getUsers();
		
		for(String k: users.keySet()){
			if(users.get(k).getEmail().equals(email)){
				return true;
			}
		}
		
		return false;
		
	}
	
	
	
	
}
