package vn.sunbuy.storyapi.model;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import vn.sunbuy.storyapi.entity.Story;

@Getter
@Setter
public class UserDTO {
	
	
	
    private String username;
    private String email;
    private String password;
    private String retypePassword;
    
    


}