package vn.sunbuy.storyapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.sunbuy.storyapi.entity.Category;
public interface CategoryRepository extends MongoRepository<Category, String> {
	Category findByCategoryCode(String categoryCode);
}
