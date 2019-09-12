package dto;


public class UserRegisterDTO {
	private String username;
	private String password;
	private String name;
	private String surname;
	private String phone;
	private String email;

	
	public UserRegisterDTO(){
		
	}


	public UserRegisterDTO(String username, String password, String name, String surname, String phone, String email) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.email = email;
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


	@Override
	public String toString() {
		return "UserRegisterDTO [username=" + username + ", password=" + password + ", name=" + name + ", surname="
				+ surname + ", phone=" + phone + ", email=" + email + "]";
	}
	
	
}
