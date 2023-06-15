package vn.sunbuy.storyapi.model;

import java.util.List;

public class JwtDTO {
	
	private String token;
	private String userId;
	private String username;
	private String email;
	private List<String> roles;
	
	public JwtDTO(String token, String username, List<String> roles) {
		this.token = token;
		this.username = username;
		this.roles = roles;
	}
	public String gettoken() {
		return token;
	}
	public void settoken(String token) {
		this.token = token;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
