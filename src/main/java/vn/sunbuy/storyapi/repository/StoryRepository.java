package vn.sunbuy.storyapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

import vn.sunbuy.storyapi.entity.Story;
public interface StoryRepository extends MongoRepository<Story, String> {
	Page<Story> findByCategoryCode(String categorycode, Pageable pageable);
	Page<Story> findBy(TextCriteria criteria, Pageable pageable);
	Page<Story> findAllByStoryNameContainingIgnoreCase(String name, Pageable pageable);
	List<Story> findByTotalChaptersLessThan(int totalChapters);

    List<Story> findByTotalChaptersBetween(int start, int end);

    List<Story> findByTotalChaptersGreaterThan(int totalChapters);
}

