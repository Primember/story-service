package vn.sunbuy.storyapi.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import vn.sunbuy.storyapi.entity.Story;
@Getter
@Setter
public class StoriesDTO {
	
	private String id;
	private String storyCode;
	private String storyName;
	private String thumbnail;
//    private String author;
	private int totalChapters;
	private List<String> categoryName;
//    private List<CategoryDTO> categories;
	
	
public static StoriesDTO transfer(Story story) {
		
		StoriesDTO storiesDTO = new StoriesDTO();
		storiesDTO.setId(story.getId());
		storiesDTO.setStoryCode(story.getStoryCode());
		storiesDTO.setStoryName(story.getStoryName());
		storiesDTO.setThumbnail(story.getThumbnail());
		storiesDTO.setTotalChapters(story.getTotalChapters());
		storiesDTO.setCategoryName(story.getCategoryName());
		return storiesDTO;
		
	}
}

