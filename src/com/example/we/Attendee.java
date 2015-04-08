package com.example.we;

public class Attendee {
	private String forename;
	private String surname;
	private long phone;
	private String email;
	
	public Attendee(String forename, String surname, long phone, String email)
	{
		this.forename = forename;
		this.surname = surname;
		this.phone = phone;
		this.email = email;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
