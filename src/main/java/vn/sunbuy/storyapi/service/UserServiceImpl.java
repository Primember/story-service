//package vn.sunbuy.storyapi.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.rest.webmvc.ResourceNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import vn.sunbuy.storyapi.entity.Users;
//import vn.sunbuy.storyapi.repository.UsersRepository;
//
//@Service
//public class UserServiceImpl implements UserService {
//	
//	@Autowired
//	private UsersRepository usersRepository;
//	
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
//	
//
//	public UserServiceImpl(UsersRepository userRepository) {
//        this.usersRepository = userRepository;
//        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
//    }
//	
//	@Override	
//	public Users save(Users user) {
//		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//		return usersRepository.save(user);
//	}
//	@Override
//	public Users updateUser(Users user, String id) {
//		Users _user = usersRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//        _user.setUsername(user.getUsername());
//        _user.setPassword(user.getPassword());
//        _user.setEmail(user.getEmail());
//        return usersRepository.save(_user);
//	}
//	@Override
//	public Users findByUsername(String username) {
//		return usersRepository.findByUsername(username);
//	}
//}
