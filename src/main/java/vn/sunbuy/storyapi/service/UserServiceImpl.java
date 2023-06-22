//package vn.sunbuy.storyapi.service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import vn.sunbuy.storyapi.common.JConstants.ERole;
//import vn.sunbuy.storyapi.entity.Role;
//import vn.sunbuy.storyapi.entity.User;
//import vn.sunbuy.storyapi.exception.InvalidPasswordException;
//import vn.sunbuy.storyapi.exception.PasswordMismatchException;
//import vn.sunbuy.storyapi.exception.UserAlreadyExistException;
//import vn.sunbuy.storyapi.exception.UserNotFoundException;
//import vn.sunbuy.storyapi.model.UserDTO;
//import vn.sunbuy.storyapi.model.UserMapper;
//import vn.sunbuy.storyapi.repository.RoleRepository;
//import vn.sunbuy.storyapi.repository.UsersRepository;
//
//@Service
//public class UserServiceImpl implements UserService {
//	  @Autowired
//	  private UsersRepository userRepository;
//	
//	  @Autowired
//	  private RoleRepository roleRepository;
//	
//	  @Autowired
//	  private PasswordEncoder passwordEncoder;
//	
//	  @Autowired
//	  private UserMapper userMapper;
//	
//	  @Override
//	  public User registerNewUserAccount(UserDTO userDTO) throws UserAlreadyExistException, PasswordMismatchException {
//	      if (userRepository.existsByUsername(userDTO.getUsername())) {
//	          throw new UserAlreadyExistException("Username already exists: " + userDTO.getUsername());
//	      }
//	
//	      if (userRepository.existsByEmail(userDTO.getEmail())) {
//	          throw new UserAlreadyExistException("Email already exists: " + userDTO.getEmail());
//	      }
//	
//	      if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
//	          throw new PasswordMismatchException("Passwords do not match");
//	      }
//	
//	      User user = userMapper.toEntity(userDTO);
//	      user.setPassword(passwordEncoder.encode(user.getPassword()));
//	      Set<Role> roles = new HashSet<>();
//	      roles.add(roleRepository.findByName(ERole.USER).orElseThrow(() -> new RuntimeException("User role not found")));
//	      user.setRoles(roles);
//	      return userRepository.save(user);
//	  }
//	
//	  @Override
//	  public User updateUserInfo(UserDTO userDTO, String username) throws UserNotFoundException, InvalidPasswordException,PasswordMismatchException {
//	      User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found: " + username));
//	
//	      if (userDTO.getEmail() != null) {
//	          user.setEmail(userDTO.getEmail());
//	      }
//	
//	      if (userDTO.getUsername() != null) {
//	          user.setUsername(userDTO.getUsername());
//	      }
//	
//	      if (userDTO.getPassword() != null && userDTO.getRetypePassword() != null) {
//	          if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
//	              throw new PasswordMismatchException("Passwords do not match");
//	          }
//	          if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
//	              throw new InvalidPasswordException("Invalid old password");
//	          }
//	          user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//	      }
//	
//	      return userRepository.save(user);
//	  }
//	
//	  @Override
//	  public User changeUserPassword(String username, String oldPassword, String newPassword) throws UserNotFoundException, InvalidPasswordException {
//	      User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found: " + username));
//	
//	      if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
//	          throw new InvalidPasswordException("Invalid old password");
//	      }
//	
//	      user.setPassword(passwordEncoder.encode(newPassword));
//	      return userRepository.save(user);
//	  }
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
////	@Autowired
////	private UsersRepository usersRepository;
////	
////	private BCryptPasswordEncoder bCryptPasswordEncoder;
////	public UserServiceImpl(UsersRepository userRepository) {
////        this.usersRepository = userRepository;
////        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
////    }
////	
////	@Override	
////	public User save(User user) {
////		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
////		return usersRepository.save(user);
////	}
////	@Override
////	public User updateUser(User user, String id) {
////		User _user = usersRepository.findById(id)
////                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản"));
////        _user.setUsername(user.getUsername());
////        _user.setPassword(user.getPassword());
////        _user.setEmail(user.getEmail());
////        return usersRepository.save(_user);
////	}
////	@Override
////	public Optional<User> findByUsername(String username) {
////		return usersRepository.findByUsername(username);
////	}
//}
