package vn.sunbuy.storyapi.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class StoryDetailDTO {
	
	private String id;
	private String storyCode;
	private String storyName;
	private String thumbnail;
//	private Long totalChapters;
//	private Long totalViews;
	private String author;
	private List<String> categoryCode;
	private String info;
	private String status; 
//	private String origin;
	private List<Chapter> chapterLinks;
//	private String rate;
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
	
}

