package vn.sunbuy.storyapi.model;

import lombok.Getter;
import lombok.Setter;
import vn.sunbuy.storyapi.entity.Category;
@Getter
@Setter
public class CategoryDTO {
	private String id;
	private String categoryName;
	private String categoryCode;
	private String description;
	private String imageUrl;
	private String title;
	
	public static CategoryDTO transfer(Category category) {
		
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setCategoryCode(category.getCategoryCode());
		categoryDTO.setCategoryName(category.getCategoryName());
		categoryDTO.setDescription(category.getDescription());
		categoryDTO.setImageUrl(category.getImageUrl());
		categoryDTO.setTitle(category.getTitle());
		return categoryDTO;
		
	}
}
