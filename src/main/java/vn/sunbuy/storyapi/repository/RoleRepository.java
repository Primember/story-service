package vn.sunbuy.storyapi.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import vn.sunbuy.storyapi.common.JConstants.ERole;
import vn.sunbuy.storyapi.entity.Role;
public interface RoleRepository extends MongoRepository<Role, String> {
	
	Optional<Role> findByName(ERole name);

}
