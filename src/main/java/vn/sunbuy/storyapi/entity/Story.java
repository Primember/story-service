package vn.sunbuy.storyapi.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import vn.sunbuy.storyapi.model.CategoryDTO;
import vn.sunbuy.storyapi.model.StoriesDTO;
import vn.sunbuy.storyapi.model.StoryDetailDTO;
@Document(collection = "stories")
@Getter
@Setter
public class Story {
	@Id
	private String id;
	private String storyLink;
	@Indexed(unique = true)
	private String storyCode;
	@TextIndexed(weight = 3)
	private String storyName;
	private String thumbnail;
	@TextIndexed
	private String author;
	private int totalChapters;
	private List<String> categoryName;
	private List<String> categoryCode;
	@TextIndexed(weight = 2)
	private String info;
	private String status;
	private String origin;
	private List<Chapter> chapterLinks;
	private Long totalViews;
	private String rate;
    private List<CategoryDTO> categories;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static class Chapter {
		private String title;
		private String link;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
	}
public StoriesDTO transferStories(Story story) {
		
		StoriesDTO storiesDTO = new StoriesDTO();
		storiesDTO.setId(story.getId());
		storiesDTO.setStoryCode(story.getStoryCode());
		storiesDTO.setStoryName(story.getStoryName());
		storiesDTO.setThumbnail(story.getThumbnail());
		storiesDTO.setTotalChapters(story.getTotalChapters());
//		storiesDTO.setCategories(story.getCategories());
		return storiesDTO;
		
	}
	public StoryDetailDTO transferStoryDetail(Story story) {
		StoryDetailDTO storyDetailDTO = new StoryDetailDTO();
	    List<StoryDetailDTO.Chapter> chapterDTOs = new ArrayList<>();
	    for (Story.Chapter chapter : story.getChapterLinks()){
	        StoryDetailDTO.Chapter chapterDTO = new StoryDetailDTO.Chapter();
	        chapterDTO.setTitle(chapter.getTitle());
	        chapterDTO.setLink(chapter.getLink());
	        chapterDTOs.add(chapterDTO);
	    }
		storyDetailDTO.setId(story.getId());
		storyDetailDTO.setStoryCode(story.getStoryCode());
		storyDetailDTO.setStoryName(story.getStoryName());
		storyDetailDTO.setThumbnail(story.getThumbnail());
		storyDetailDTO.setAuthor(story.getAuthor());
		storyDetailDTO.setCategoryCode(story.getCategoryCode());
		storyDetailDTO.setInfo(story.getInfo());
		storyDetailDTO.setStatus(story.getStatus());
//		storyDetailDTO.setOrigin(story.getOrigin());
//		storyDetailDTO.setRate(story.getRate());
	    storyDetailDTO.setChapterLinks(chapterDTOs);
		return storyDetailDTO;
		
	}
	
}
