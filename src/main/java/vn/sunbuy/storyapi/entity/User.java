package vn.sunbuy.storyapi.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Document(collection = "users")
public class User {
	
	@Id
	public String id;
	@NotBlank
	public String username;
	@NotBlank
	public String password;
	@NotBlank
	@Email
	private String email;
	@DBRef
    private Set<Role> roles = new HashSet<>();
	
	public User(String id, String username, String password, String email, Set<Role> roles) {
	    this.id = id;
	    this.username = username;
	    this.password = password;
	    this.email = email;
	    this.roles = roles;
	}
	public User( String username, String password, String email, Set<Role> roles) {
	    
	    this.username = username;
	    this.password = password;
	    this.email = email;
	    this.roles = roles;
	}

}
