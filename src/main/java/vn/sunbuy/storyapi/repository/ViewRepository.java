package vn.sunbuy.storyapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.sunbuy.storyapi.entity.View;
public interface ViewRepository extends MongoRepository<View, String> {

}
