package vn.sunbuy.storyapi.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class FullStoriesDTO {
	private List<StoriesDTO> topStories;
	private List<StoriesDTO> newStories;
	private List<StoriesDTO> completedStories;
	private List<StoriesDTO> topStoryByDays;
	private List<CategoryDTO> categorys;
	
}
