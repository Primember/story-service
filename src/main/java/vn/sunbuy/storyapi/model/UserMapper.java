package vn.sunbuy.storyapi.model;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import vn.sunbuy.storyapi.entity.User;
public class UserMapper {
	@Autowired
    private ModelMapper modelMapper;
	public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}