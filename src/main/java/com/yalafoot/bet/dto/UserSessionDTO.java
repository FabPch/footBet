package com.yalafoot.bet.dto;

public class UserSessionDTO {
	private String login;
	private String name;
	
	
	public UserSessionDTO(String login, String name) {
		super();
		this.login = login;
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
