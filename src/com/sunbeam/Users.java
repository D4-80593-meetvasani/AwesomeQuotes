package com.sunbeam;

public class Users {
	private int id;
	private String Fname;
	private String Lname;
	private String email;
	private String password;
	private String mobile;
	public Users(int id, String fname, String lname, String email, String password, String mobile) {
		super();
		this.id = id;
		Fname = fname;
		Lname = lname;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
	}
	public Users() {
		
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFname() {
		return Fname;
	}
	public void setFname(String fname) {
		Fname = fname;
	}
	public String getLname() {
		return Lname;
	}
	public void setLname(String lname) {
		Lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", Fname=" + Fname + ", Lname=" + Lname + ", email=" + email + ", password="
				+ password + ", mobile=" + mobile + "]";
	}
	
	
	
}
