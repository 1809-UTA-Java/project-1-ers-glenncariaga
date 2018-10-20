package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ERS_USERS", schema ="ERSADMIN")
public class ERS_Users {
	@Id
	@Column(name = "U_ID")
	private String id;
	@Column(name = "U_USERNAME")
	private String username;
	@Column(name = "U_PASSWORD")
	private String password;
	@Column(name = "U_FIRSTNAME")
	private String firstName;
	@Column(name = "U_LASTNAME")
	private String lastName;
	@Column(name = "U_EMAIL")
	private String email;
	@Column(name = "U_ROLE")
	private String userRole;
	
	
	
	public ERS_Users(String id, String username, String password, String firstName, String lastName, String email,
			String userRole) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userRole = userRole;
	}
	
	public ERS_Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", userRole=" + userRole + "]";
	}
	
	
}
