package com.universe.softplat.sample;

/**
 * 用户对象
 */
public class UserObject {
	private String userName,password,role;
	
	public UserObject(String userName,String password,String role){
		this.userName = userName;
		this.password = password;
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin(){
		return "admin".equalsIgnoreCase(role);
	}
}
