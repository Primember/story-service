package vn.sunbuy.storyapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.sunbuy.storyapi.entity.Category;
import vn.sunbuy.storyapi.entity.Story;
import vn.sunbuy.storyapi.model.CategoryDTO;
import vn.sunbuy.storyapi.model.StoriesDTO;
@Service
public interface CategoryService {
	Category createCategory(Category category);
	List<CategoryDTO> getAllCategories();
    Category updateCategory(Category category, String id);
    Page<StoriesDTO> getStoriesByCategoryCode(String categoryCode, int page, int size);
    void deleteCategory(String id);
    
}
