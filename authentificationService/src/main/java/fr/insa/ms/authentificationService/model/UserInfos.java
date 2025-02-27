package fr.insa.ms.authentificationService.model;


public class UserInfos {
	
	private int id;
	private String lastName;
	private String firstName;
	private String location;
	private String helpStatus;
	private String password;
	
	public UserInfos() {}


	public UserInfos(int id, String lastName, String firstName, String location, String helpStatus, String password) {
	    this.id = id;
	    this.lastName = lastName;
	    this.firstName = firstName;
	    this.location = location;
	    this.helpStatus = helpStatus;
	    this.password = password;
	}
	public int getId(){
	    return id;
	}
	public void setId (int id) {
	    this.id = id;
	}
	public String getLastName() {
	    return lastName;
	}
	public void setLastName(String lastName) {
	    this.lastName = lastName;
	}
	public String getFirstName() {
	    return firstName;
	}
	public void setFirstName(String firstName) {
	    this.firstName = firstName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getHelpStatus() { 
		return helpStatus; 
	} 
	public void setHelpStatus(String helpStatus) {
		this.helpStatus = helpStatus; 
	}
	public String getPassword() { 
		return password;
	}
	public void setPassword(String password) {
		this.password = password; 
	}
}


