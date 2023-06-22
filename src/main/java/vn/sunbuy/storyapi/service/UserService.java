package vn.sunbuy.storyapi.service;

import org.springframework.stereotype.Service;

import vn.sunbuy.storyapi.entity.User;
import vn.sunbuy.storyapi.exception.InvalidPasswordException;
import vn.sunbuy.storyapi.exception.PasswordMismatchException;
import vn.sunbuy.storyapi.exception.UserAlreadyExistException;
import vn.sunbuy.storyapi.exception.UserNotFoundException;
import vn.sunbuy.storyapi.model.UserDTO;

@Service
public interface UserService {
    User registerNewUserAccount(UserDTO userDTO) throws UserAlreadyExistException, PasswordMismatchException;
    User updateUserInfo(UserDTO userDTO, String username) throws UserNotFoundException, InvalidPasswordException, PasswordMismatchException;
    User changeUserPassword(String username, String oldPassword, String newPassword) throws UserNotFoundException, InvalidPasswordException, PasswordMismatchException;
//	User save(User user);
//
//    User findByUsername(String username);
//    
//    User updateUser(User user, String id);

	
}
