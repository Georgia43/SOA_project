package fr.insa.ms.notificationService.model;

public class Notification {
	
	private int id;
	private int idUser;
	private String message;
	private String status;
	private String date;
	
	public Notification() {}
	
	public Notification(int id, int idUser, String message, String status, String date) {
		this.id = id;
		this.idUser = idUser;
		this.message = message;
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
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
