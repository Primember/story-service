package vn.sunbuy.storyapi.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChapterDTO {
	
	private String id;
	private String chapterTitle;
	private String chapterLink;
	private String htmlcontent;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChapterTitle() {
		return chapterTitle;
	}
	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}
	public String getChapterLink() {
		return chapterLink;
	}
	public void setChapterLink(String chapterLink) {
		this.chapterLink = chapterLink;
	}
	public String getHtmlcontent() {
		return htmlcontent;
	}
	public void setHtmlcontent(String htmlcontent) {
		this.htmlcontent = htmlcontent;
	}
	

	
}
