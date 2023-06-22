package vn.sunbuy.storyapi.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.sunbuy.storyapi.common.JConstants.ERole;
import vn.sunbuy.storyapi.entity.Role;
import vn.sunbuy.storyapi.entity.User;
import vn.sunbuy.storyapi.exception.InvalidPasswordException;
import vn.sunbuy.storyapi.exception.PasswordMismatchException;
import vn.sunbuy.storyapi.exception.UserAlreadyExistException;
import vn.sunbuy.storyapi.exception.UserNotFoundException;
import vn.sunbuy.storyapi.model.UserDTO;
import vn.sunbuy.storyapi.model.UserMapper;
import vn.sunbuy.storyapi.repository.RoleRepository;
import vn.sunbuy.storyapi.repository.UsersRepository;

@Service
public class UserDetailsImpl implements UserDetailsService  {
	
	@Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                getAuthority(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> getAuthority(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }
}
//	@Autowired
//    private UsersRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Override
//    public User registerNewUserAccount(UserDTO userDTO) throws UserAlreadyExistException, PasswordMismatchException {
//        if (userRepository.existsByUsername(userDTO.getUsername())) {
//            throw new UserAlreadyExistException("Username already exists: " + userDTO.getUsername());
//        }
//
//        if (userRepository.existsByEmail(userDTO.getEmail())) {
//            throw new UserAlreadyExistException("Email already exists: " + userDTO.getEmail());
//        }
//
//        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
//            throw new PasswordMismatchException("Passwords do not match");
//        }
//
//        User user = userMapper.toEntity(userDTO);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        Set<Role> roles = new HashSet<>();
//        roles.add(roleRepository.findByName(ERole.USER).orElseThrow(() -> new RuntimeException("User role not found")));
//        user.setRoles(roles);
//        return userRepository.save(user);
//    }
//
//    @Override
//    public User updateUserInfo(UserDTO userDTO, String username) throws UserNotFoundException, InvalidPasswordException,PasswordMismatchException {
//        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found: " + username));
//
//        if (userDTO.getEmail() != null) {
//            user.setEmail(userDTO.getEmail());
//        }
//
//        if (userDTO.getUsername() != null) {
//            user.setUsername(userDTO.getUsername());
//        }
//
//        if (userDTO.getPassword() != null && userDTO.getRetypePassword() != null) {
//            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
//                throw new PasswordMismatchException("Passwords do not match");
//            }
//            if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
//                throw new InvalidPasswordException("Invalid old password");
//            }
//            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        }
//
//        return userRepository.save(user);
//    }
//
//    @Override
//    public User changeUserPassword(String username, String oldPassword, String newPassword) throws UserNotFoundException, InvalidPasswordException {
//        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found: " + username));
//
//        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
//            throw new InvalidPasswordException("Invalid old password");
//        }
//
//        user.setPassword(passwordEncoder.encode(newPassword));
//        return userRepository.save(user);
//    }
//import java.util.Collection;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import vn.sunbuy.storyapi.entity.User;
//import vn.sunbuy.storyapi.common.JConstants.ERole;
//public class UserDetailsImpl implements UserDetails {
//	
//
//	private static final long serialVersionUID = 1L;
//
//	private String id;
//
//	private String username;
//
//	private String email;
//
//	@JsonIgnore
//	private String password;
//	private ERole roles;
////
////	private Collection<? extends GrantedAuthority> authorities;
//
//	public UserDetailsImpl(String id, String username, String email, String password,
//			Collection<? extends GrantedAuthority> authorities) {
//		this.id = id;
//		this.username = username;
//		this.email = email;
//		this.password = password;
////		this.authorities = authorities;
//	}
//
//	public static UserDetailsImpl build(User user) {
//		List<GrantedAuthority> authorities = user.getRoles().stream()
//				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
//				.collect(Collectors.toList());
//
//		return new UserDetailsImpl(
//				user.getId(), 
//				user.getUsername(), 
//				user.getEmail(),
//				user.getPassword(),
//				authorities);
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//			return List.of(new SimpleGrantedAuthority(roles.name()));
//		}
//
//
//
//	public String getId() {
//		return id;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		UserDetailsImpl user = (UserDetailsImpl) o;
//		return Objects.equals(id, user.id);
//	}
//
//}
