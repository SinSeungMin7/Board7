package com.green.user.dto;

public class UserDto {
	// Fields
	private String  userid;
	private String  passwd;
	private String  username;
	private String  email;
	private int     upoint;
	private String  regdate;
	
	//Getter / Setter
	public UserDto() {}
	public UserDto(String userid, String passwd, String username, 
			String email, int upoint, String regdate) {
		this.userid = userid;
		this.passwd = passwd;
		this.username = username;
		this.email = email;
		this.upoint = upoint;
		this.regdate = regdate;
	}
	
	//Getter / Setter
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getUpoint() {
		return upoint;
	}
	public void setUpoint(int upoint) {
		this.upoint = upoint;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	//toString
	@Override
	public String toString() {
		return "UserDto [userid=" + userid + ", passwd=" + passwd + ", username=" + username + ", email=" + email
				+ ", upoint=" + upoint + ", regdate=" + regdate + "]";
	}	
}
