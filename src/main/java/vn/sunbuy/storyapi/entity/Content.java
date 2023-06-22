package vn.sunbuy.storyapi.entity;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Document(collection = "content")
public class Content {
	private String id;
	private String storyCode;
	private String storyName;
	private String author;
	private String categoryCode;
	private String categoryName;
	private String status;
	private String rate;
	private List<Chapter> content;
	public static class Chapter {
		private String chapterTitle;
		private String chapterlink;
		private String htmlContent;
		public String getChapterTitle() {
			return chapterTitle;
		}
		public void setChapterTitle(String chapterTitle) {
			this.chapterTitle = chapterTitle;
		}
		public String getChapterlink() {
			return chapterlink;
		}
		public void setChapterlink(String chapterlink) {
			this.chapterlink = chapterlink;
		}
		public String getHtmlContent() {
			return htmlContent;
		}
		public void setHtmlContent(String htmlContent) {
			this.htmlContent = htmlContent;
		}
	}
}
