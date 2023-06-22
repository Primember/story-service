//package vn.sunbuy.storyapi.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import jakarta.validation.Valid;
//import vn.sunbuy.storyapi.common.URI;
//import vn.sunbuy.storyapi.entity.User;
//import vn.sunbuy.storyapi.exception.InvalidPasswordException;
//import vn.sunbuy.storyapi.exception.UserAlreadyExistException;
//import vn.sunbuy.storyapi.exception.UserNotFoundException;
//import vn.sunbuy.storyapi.model.ChangePasswordRequest;
//import vn.sunbuy.storyapi.model.LoginRequest;
//import vn.sunbuy.storyapi.model.UserDTO;
//import vn.sunbuy.storyapi.model.UserMapper;
//import vn.sunbuy.storyapi.security.JwtTokenUtil;
//import vn.sunbuy.storyapi.service.UserService;
//
//@RestController
//@RequestMapping(value =URI.USER)
//public class UserController {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private UserMapper userMapper;
//
//    @PostMapping(URI.SIGNUP)
//    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) throws UserAlreadyExistException {
//        User user = userService.registerNewUserAccount(userDTO);
//        return ResponseEntity.ok(userMapper.toDTO(user));
//    }
//
//    @PostMapping(URI.SIGNIN)
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtTokenUtil.generateToken(authentication);
//        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
//    }
//
//    @PutMapping(URI.PROFILE)
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UserDTO userDTO) throws UserNotFoundException, InvalidPasswordException {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User updatedUser = userService.updateUserInfo(userDTO, username);
//        return ResponseEntity.ok(userMapper.toDTO(updatedUser));
//    }
//
//    @PutMapping(URI.PASSWORD)
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<?> changeUserPassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) throws UserNotFoundException, InvalidPasswordException {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User updatedUser = userService.changeUserPassword(username, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
//        return ResponseEntity.ok(userMapper.toDTO(updatedUser));
//    }
//    
//}
////public class UserController {
////	@Autowired
////	 UsersRepository userRepository;
////	@Autowired
////	 UserService userService;
////	@PostMapping(URI.CREATE)
////	public User createAuthor(@RequestBody User user) {
////	  return userService.save(user);
////	}
////	
////	
////	@PutMapping(URI.UPDATE + URI.ID)
////
////    public User updateUser(@RequestBody User user,@PathVariable String id) {
////		  return userService.save(user);
////
////    }
////	
////	@GetMapping(URI.SEARCH)
////	public User findByUsername(@RequestParam(name = "username") String  username) {
////		return userService.findByUsername(username);
////		
////	}
////	public List<User> getAll(@RequestParam(required = false) int page,  @RequestParam(required = false) int size) {
////		return userRepository.findAll();
////	}
////	@DeleteMapping(URI.ID)
////    public void deleteAuthor(@PathVariable String id) {
////        userRepository.deleteById(id);
////    }
////	
////
////}
