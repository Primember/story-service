//package vn.sunbuy.storyapi.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import vn.sunbuy.storyapi.common.URI;
//import vn.sunbuy.storyapi.entity.Users;
//import vn.sunbuy.storyapi.repository.UsersRepository;
//import vn.sunbuy.storyapi.service.UserService;
//
//@RestController
//@RequestMapping(value = URI.V1 + URI.USER)
//public class UserController {
//	@Autowired
//	 UsersRepository userRepository;
//	@Autowired
//	 UserService userService;
//	@PostMapping(URI.CREATE)
//	public Users createAuthor(@RequestBody Users user) {
//	  return userService.save(user);
//	}
//	
//	
//	@PutMapping(URI.UPDATE + URI.ID)
//
//    public Users updateUser(@RequestBody Users user,@PathVariable String id) {
//		  return userService.save(user);
//
//    }
//	
//	@GetMapping(URI.SEARCH)
//	public Users findByUsername(@RequestParam(name = "username") String  username) {
//		return userService.findByUsername(username);
//		
//	}
//	public List<Users> getAll(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
//		System.out.println(page + "aaaa" + size);
//		// Pageable pageable = PageRequest.of(page, size);
//		return userRepository.findAll();
//	}
//	@DeleteMapping(URI.ID)
//    public void deleteAuthor(@PathVariable String id) {
//        userRepository.deleteById(id);
//    }
//	
//
//}
