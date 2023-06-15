//package vn.sunbuy.storyapi.controller;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import vn.sunbuy.storyapi.common.URI;
//import vn.sunbuy.storyapi.common.JConstants.ERole;
//import vn.sunbuy.storyapi.entity.Role;
//import vn.sunbuy.storyapi.entity.Users;
//import vn.sunbuy.storyapi.model.JwtDTO;
//import vn.sunbuy.storyapi.model.LoginRequest;
//import vn.sunbuy.storyapi.model.SignupRequest;
//import vn.sunbuy.storyapi.repository.RoleRepository;
//import vn.sunbuy.storyapi.repository.UsersRepository;
//import vn.sunbuy.storyapi.security.JwtUtils;
//
//@RestController
//@RequestMapping(URI.V1 + URI.AUTH)
//public class AuthController {
//
////	@Autowired
////	private UserService userService;
////	
//	@Autowired
//	AuthenticationManager authenticationManager;
//
//	@Autowired
//	UsersRepository userRepository;
//
//	@Autowired
//	RoleRepository roleRepository;
//
//	@Autowired
//	PasswordEncoder encoder;
//
//	@Autowired
//	JwtUtils jwtUtils;
//	
//	@RequestMapping(value = URI.SIGNUP, method = RequestMethod.POST)
//	public Users createUser(@RequestBody Users user) {
//	  return userRepository.save(user);
//	}
//
//	
//	@RequestMapping(value = URI.LOGIN, method = RequestMethod.POST)
//	public JwtDTO authenticateUser(@Valid @RequestBody LoginRequest user) {
//		
//		
//		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		
//		String jwt = jwtUtils.generateJwtToken(authentication);
//		
//		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
//
//		List<String> roles = userDetails.getAuthorities().stream()
//				.map(item -> item.getAuthority())
//				.collect(Collectors.toList());
//		JwtDTO jwtDto = new JwtDTO(jwt, userDetails.getUsername(), roles);
//
//		return jwtDto;
//	}
//	
//	@RequestMapping(value = URI.SIGNUP, method = RequestMethod.POST)
//	public String registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//
//
//		// Create new user's account
//		
//		Users user = new Users(signUpRequest.getUsername(), 
//							 signUpRequest.getEmail(),
//							 encoder.encode(signUpRequest.getPassword()));
//		System.out.print(signUpRequest.getRoles());
//
//		Set<String> strRoles = new HashSet<>(signUpRequest.getRoles());
//		Set<Role> roles = new HashSet<>();
//
//		if (CollectionUtils.isEmpty(strRoles)) {
//			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//			roles.add(userRole);
//		} else {
//			strRoles.forEach(role -> {
//				switch (role) {
//				case "admin":
//					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(adminRole);
//
//					break;
//				default:
//					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(userRole);
//				}
//			});
//		}
//
//		user.setRoles(roles);
//		userRepository.save(user);
//
//		return ("User registered successfully!");
//	}
//	
//}
