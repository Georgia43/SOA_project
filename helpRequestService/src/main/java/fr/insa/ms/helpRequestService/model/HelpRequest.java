package fr.insa.ms.helpRequestService.model;

public class HelpRequest {
	private int id;
	private int idUser;
	private int idHelper;
	private String content;
	private String location;
	private String status;
	private String date;
	
	public HelpRequest() {}
	
	public HelpRequest(int id, int idUser, int idHelper, String content, String location, String status, String date) {
		this.id = id;
		this.idUser = idUser;
		this.idHelper = idHelper;
		this.content = content;
		this.location = location;
		this.status = status;
		this.date = date;
	}
	public int getId(){
	    return id;
	}
	public void setId (int id) {
	    this.id = id;
	}
	public int getIdUser(){
	    return idUser;
	}
	public void setIdUser (int idUser) {
	    this.idUser = idUser;
	}
	public int getIdHelper(){
	    return idHelper;
	}
	public void setIdHelper (int idHelper) {
	    this.idHelper = idHelper;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location=location;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status; 
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date; 
	}
}
