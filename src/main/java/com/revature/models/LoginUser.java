package com.revature.models;

public class LoginUser {
	
	private String username;
	private String password;

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

	@Override
	public String toString() {
		return "LoginUser [username=" + username + ", password=" + password + "]";
	}

	public LoginUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public LoginUser() {
		super();
		// TODO Auto-generated constructor stub
	}

}
