package vn.sunbuy.storyapi.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.sunbuy.storyapi.entity.Story;
import vn.sunbuy.storyapi.entity.Content;
import vn.sunbuy.storyapi.model.ContentDTO;
import vn.sunbuy.storyapi.model.FullStoriesDTO;
//import vn.sunbuy.storyapi.model.FullStoriesDTO;
import vn.sunbuy.storyapi.model.StoriesDTO;
import vn.sunbuy.storyapi.model.StoriesResult;
import vn.sunbuy.storyapi.model.StoryDetailDTO;
@Service
public interface StoryService {
	public FullStoriesDTO getTopAndFullStory();
	Story createStory(Story story);
	Page<StoriesDTO> getAllStories(int page, int size);
    Story getStoryById(String id);
    Story updateStory(Story story, String id);
	public Page<StoriesDTO> getNewStories(Pageable page);
	public Page<StoriesDTO> getCompletedStories(Pageable page);
	public Page<StoriesResult> searchStories(String textSearch, Pageable pageable);

	public Page<Story> getStoryByCategory(String categoryCode, Pageable page);
	public Page<StoriesDTO> getTopStoryByCategory(String categoryId, Pageable page);
	public StoryDetailDTO getStoryDetail(String storyId);
	public List<StoriesDTO> getTopStoriesByDay();
	public List<StoriesDTO> getTopStoriesByMonth();
	public List<StoriesDTO> getTopStoriesByYear();
//	public List<StoriesDTO> searchNewStory(String authorSearch, String categorySeach, int totalChapter);
	public void updateViewStory(String storyId);
	public Page<ContentDTO.Chapter> getContentStory(String storyCode, Pageable pageable );
	public void deleteStory(String id);
	
}
