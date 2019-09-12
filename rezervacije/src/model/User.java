package model;

import dto.UserRegisterDTO;
import enumerations.UserType;

public class User {
	private String username;
	private String password;
	private String name;
	private String surname;
	private String phone;
	private String email;
	private boolean active;
	private UserType userType;
	
	
	public User(){
		
	}
	
	
	public User(String username, String password, String name, String surname, String phone, String email,
			 boolean active, UserType userType) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.email = email;
		this.active = active;
		this.userType = userType;
	}

	public User(UserRegisterDTO userRegisterDTO){
		this.username = userRegisterDTO.getUsername();
		this.password = userRegisterDTO.getPassword();
		this.name = userRegisterDTO.getName();
		this.surname = userRegisterDTO.getSurname();
		this.phone = userRegisterDTO.getPhone();
		this.email = userRegisterDTO.getEmail();
		this.active = true;
		this.userType = UserType.CLIENT;
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public UserType getUserType() {
		return userType;
	}


	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", name=" + name + ", surname=" + surname
				+ ", phone=" + phone + ", email=" + email + ", active=" + active + ", userType=" + userType + "]";
	}
	
	
	
	
	
	
	
}
