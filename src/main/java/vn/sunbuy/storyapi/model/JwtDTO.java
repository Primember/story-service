package vn.sunbuy.storyapi.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
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

}
