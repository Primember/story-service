//package vn.sunbuy.storyapi.service;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import vn.sunbuy.storyapi.entity.User;
//import vn.sunbuy.storyapi.repository.UsersRepository;
//
//@Component
//public class MongoUserDetailsService implements UserDetailsService {
//
//	
//	@Autowired
//	private UsersRepository usersRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		User user = usersRepository.findByUsername(username);
//		if(user == null) {
//		  throw new UsernameNotFoundException("Không tìm thấy");
//		}
//		List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
//		return new User(user.getUsername(), user.getPassword(), authorities,user.getAvatar());
//	}
//
//}
