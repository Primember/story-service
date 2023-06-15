package vn.sunbuy.storyapi.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import vn.sunbuy.storyapi.entity.Story;
@Getter
@Setter
public class StoriesResult {
	
	private String id;
	private String storyCode;
	private String storyName;
	private String thumbnail;
    private String author;
	private int totalChapters;
	private List<String> categoryName;
//    private List<CategoryDTO> categories;
	
	
public static StoriesResult transfer(Story story) {
		
		StoriesResult storiesResult = new StoriesResult();
		storiesResult.setId(story.getId());
		storiesResult.setStoryCode(story.getStoryCode());
		storiesResult.setStoryName(story.getStoryName());
		storiesResult.setThumbnail(story.getThumbnail());
		storiesResult.setAuthor(story.getAuthor());
		storiesResult.setTotalChapters(story.getTotalChapters());
		storiesResult.setCategoryName(story.getCategoryName());
		return storiesResult;
		
	}
}
