package vn.sunbuy.storyapi.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.sunbuy.storyapi.entity.Role;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SignupRequest {
	private String username;
	private String password;
	private String email;
	private Role roles;
	
}
