package com.sunbeam;

public class Quotes {
	private int id;
	private String text;
	private String author;
	private int userId;
	private int likeCount;

	public Quotes(int id, String text, String author, int userId) {
		super();
		this.id = id;
		this.text = text;
		this.author = author;
		this.userId = userId;

	}

	public Quotes(int id, String text, String author, int userId, int likeCount) {
		super();
		this.id = id;
		this.text = text;
		this.author = author;
		this.userId = userId;
		this.likeCount = likeCount;
	}

	public Quotes() {

		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		String likeStatus = likeCount > 0 ? "♥" : "♡";
		return likeStatus + " Quotes [id=" + id + ", text=" + text + ", author=" + author + ", userId=" + userId
				+ ", likeCount=" + likeCount + "]";
	}

}
