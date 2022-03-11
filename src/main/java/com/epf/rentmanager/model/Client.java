package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.time.Period;

public class Client {
	
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private LocalDate birthDate;
	
	public Client() {
		
	}
	
	public Client(int id, String lastname, String firstname, String email, LocalDate birthDate) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.birthDate = birthDate;
	}
	
	public String toString() {
		return "\n----------------------------------------------------" 
				+ "\nid : " + id
				+ "\nLast name : " + lastname
				+ "\nFirst name : " + firstname
				+ "\nemail : " + email
				+ "\nBirthday : " + birthDate;
	}
	public int getId() {
		return id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String name) {
		this.lastname = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public int getAge() {
		LocalDate now = LocalDate.now();
		return Period.between(this.birthDate, now).getYears();
	}

	public boolean isLegal() {
		return this.getAge() >= 18;
	}
}
