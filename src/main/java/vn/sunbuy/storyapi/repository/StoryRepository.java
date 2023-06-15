package vn.sunbuy.storyapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import vn.sunbuy.storyapi.entity.Story;
public interface StoryRepository extends MongoRepository<Story, String> {
	Page<Story> findByCategoryCode(String categorycode, Pageable pageable);
	Page<Story> findByAuthor(String author, Pageable pageable);
}

