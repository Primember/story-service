package vn.sunbuy.storyapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import vn.sunbuy.storyapi.entity.Users;

public interface UsersRepository extends MongoRepository<Users, String> {
	Users findByUsername(String username);
	Boolean existsByUsername(String username);

}
