package fr.insa.ms.FeedbackService.model;

public class Feedback {
	private int id;
	private int idUser;
	private String content;
	private int rating;
	
	public Feedback() {}
	
	public Feedback(int id, int idUser, String content, int rating) {
		this.id = id;
		this.idUser = idUser;
		this.content = content;
		this.rating = rating;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
