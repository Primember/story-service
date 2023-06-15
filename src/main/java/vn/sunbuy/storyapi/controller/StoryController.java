package vn.sunbuy.storyapi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.sunbuy.storyapi.common.URI;
import vn.sunbuy.storyapi.entity.Story;
import vn.sunbuy.storyapi.model.ContentDTO;
import vn.sunbuy.storyapi.model.StoriesDTO;
import vn.sunbuy.storyapi.model.StoriesResult;
import vn.sunbuy.storyapi.model.StoryDetailDTO;
import vn.sunbuy.storyapi.repository.StoryRepository;
import vn.sunbuy.storyapi.service.StoryService;
@RestController
@RequestMapping(URI.STORY)
public class StoryController {
	
	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private StoryService storyService;
	@PostMapping(URI.CREATE)
	public Story createStory(@RequestBody Story story) {
	  return storyService.createStory(story);
	}
	@PutMapping(URI.UPDATE + URI.ID)

    public Story updateCategory(@RequestBody Story story,@PathVariable String id) {
		  return storyService.updateStory(story, id);

    }
	
	@GetMapping(URI.GETALL)
	public Page<StoriesDTO> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
		return storyService.getAllStories(page, size);
	}
	
	@GetMapping(URI.SUCCESS)
	public String createStory() {
	  return "success";
	}
	
//	@GetMapping(URI.HOME)
//	public FullStoriesDTO home() {
//		return storyService.getTopAndFullStory();
//	}
	
	@GetMapping(URI.GETNEWSTORIES)
	public Page<StoriesDTO> getNewStories(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "16") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return storyService.getNewStories(pageable);
	}
	
	@GetMapping(URI.GETCOMPLETEDSTORIES)
	public Page<StoriesDTO> getCompletedStories(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return storyService.getCompletedStories(pageable);
	}
	
//	@GetMapping(URI.GETSTORYBYCATEGORY)
//	public Page<StoriesDTO> getStoryByCategory(@RequestParam(name = "categoryId") String categoryId, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
//		Pageable pageable = PageRequest.of(page, size);
//		return storyService.getStoryByCategory(categoryId, pageable);
//	}
	
	@GetMapping(URI.GETTOPSTORYBYCATEGORY)
	public Page<StoriesDTO> getTopStoryByCategory(@RequestParam(name = "categoryId") String categoryId, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return storyService.getTopStoryByCategory(categoryId, pageable);
	}
//	@GetMapping(URI.GETSTORYBYAUTHORNAME)
//	public ResponseEntity<Page<Story>> getStoryByAuthorName(@PathVariable String author, @RequestParam(defaultValue = "0") int page, 
//			@RequestParam(defaultValue = "10") int size) {
//        return ResponseEntity.ok(storyService.getStoriesByAuthor(author, page, size));
//    }	
	@GetMapping(URI.SEARCH)
	public Page<StoriesResult> searchStory(@RequestParam(name = "textSearch") String textSearch, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return storyService.searchStories(textSearch, pageable);
	}
	@GetMapping(URI.GETSTORYDETAIL)
	public StoryDetailDTO getStoryDetail(@RequestParam(name = "storyId") String storyId) {
		return storyService.getStoryDetail(storyId);
	}	
	@PostMapping(URI.UPDATEORCREATEVIEW)
	public void updateOrCreateView(@RequestParam(name = "storyId") String storyId) {
		System.out.print("storyId");
		System.out.print(storyId);
		storyService.updateViewStory(storyId);
	}
	@GetMapping(URI.GETTOPDAY)
	public List<StoriesDTO> getTopDay() {
		return storyService.getTopStoriesByDay();
	}
	@GetMapping(URI.GETTOPMONTH)
	public List<StoriesDTO> getTopMonth() {
		return storyService.getTopStoriesByMonth();
	}
	
	@GetMapping(URI.GETTOPYEAR)
	public List<StoriesDTO> getTopYear() {
		return storyService.getTopStoriesByYear();
	}
	@GetMapping(URI.GETCONTENSTORY)
	public Page<ContentDTO.Chapter> getContentStory(@PathVariable String storyCode,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return storyService.getContentStory(storyCode, pageable);
	}
	@DeleteMapping(URI.ID)
    public void deleteStory(Story story,@PathVariable String id) {
        storyRepository.deleteById(id);
    }
	
}
