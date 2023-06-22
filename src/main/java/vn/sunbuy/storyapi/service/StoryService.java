package vn.sunbuy.storyapi.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.sunbuy.storyapi.entity.Story;
import vn.sunbuy.storyapi.model.ContentDTO;
import vn.sunbuy.storyapi.model.StoriesDTO;
import vn.sunbuy.storyapi.model.StoriesResult;
import vn.sunbuy.storyapi.model.StoryDetailDTO;
@Service
public interface StoryService {
	Story createStory(Story story);
	Page<StoriesDTO> getAllStories(int page, int size);
    Story getStoryById(String id);
    Story updateStory(Story story, String id);
	public Page<StoriesDTO> getNewStories(Pageable page);
	public Page<StoriesDTO> getCompletedStories(Pageable page);
	public Page<StoriesResult> searchStories(String searchText, int page, int size);
	public Page<Story> getStoryByCategory(String categoryCode, Pageable page);
	public Page<StoriesDTO> getTopStoryByCategory(String categoryId, Pageable page);
	public StoryDetailDTO getStoryDetail(String storyCode, int page, int size);
	public void updateViewStory(String storyId);
	public ContentDTO getContentStory(String storyCode, int chapterNumber);
	
	List<StoriesDTO> filterByTotalChapters(String filter);
	public List<StoriesDTO> getTopStory(LocalDate startDate, LocalDate endDate);
	public List<StoriesDTO> getTopStoriesByTimes(String range);
	
}
