package dto;

public class UserBlockDTO {
	private String username;
	
	public UserBlockDTO(){
		
	}

	public UserBlockDTO(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
