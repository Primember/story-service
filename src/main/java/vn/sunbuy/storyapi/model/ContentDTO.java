package vn.sunbuy.storyapi.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import vn.sunbuy.storyapi.entity.Content;
import vn.sunbuy.storyapi.entity.Story;
@Getter
@Setter
public class ContentDTO {
	
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
		private String htmlContent;
		public String getChapterTitle() {
			return chapterTitle;
		}
		public void setChapterTitle(String chapterTitle) {
			this.chapterTitle = chapterTitle;
		}
		public String getHtmlContent() {
			return htmlContent;
		}
		public void setHtmlContent(String htmlContent) {
			this.htmlContent = htmlContent;
		}
	}
public ContentDTO transfer(Content content) {
	List<ContentDTO.Chapter> chapterDTOs = new ArrayList<>();
    for (Content.Chapter chapter : content.getContent()){
        ContentDTO.Chapter chapterDTO = new ContentDTO.Chapter();
        chapterDTO.setChapterTitle(chapter.getChapterTitle());
        chapterDTO.setHtmlContent(chapter.getHtmlContent());
        chapterDTOs.add(chapterDTO);
    }
		ContentDTO contentDTO = new ContentDTO();
		contentDTO.setId(content.getId());
		contentDTO.setStoryCode(content.getStoryCode());
		contentDTO.setStoryName(content.getStoryName());
		contentDTO.setAuthor(content.getAuthor());
		contentDTO.setCategoryCode(content.getCategoryCode());
		contentDTO.setCategoryName(content.getCategoryName());
		contentDTO.setStatus(content.getStatus());
		contentDTO.setRate(content.getRate());
		contentDTO.setContent(chapterDTOs);
		return contentDTO;
		
	}
}

