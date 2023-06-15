package vn.sunbuy.storyapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import vn.sunbuy.storyapi.entity.Category;
import vn.sunbuy.storyapi.entity.Story;
import vn.sunbuy.storyapi.model.CategoryDTO;
import vn.sunbuy.storyapi.model.StoriesDTO;
import vn.sunbuy.storyapi.repository.CategoryRepository;
import vn.sunbuy.storyapi.repository.StoryRepository;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private StoryRepository storyRepository;
	@Override
	public Category createCategory(Category category) {
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        return categoryRepository.save(category);
	}
	@Override
    public List<CategoryDTO> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDTO> categoryDTOs = categories.stream()
	            .map(CategoryDTO::transfer)
	            .collect(Collectors.toList());
		return categoryDTOs;
    } 

    @Override
    public Category updateCategory(Category category, String id) {
        Category _category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        _category.setCategoryName(category.getCategoryName());
        _category.setCategoryCode(category.getCategoryCode());
        _category.setImageUrl(category.getImageUrl());
        _category.setTitle(category.getTitle());
        return categoryRepository.save(_category);
    }
    @Override
    public Page<StoriesDTO> getStoriesByCategoryCode(String categoryCode, int page, int size) {
    	Category category = categoryRepository.findByCategoryCode(categoryCode);
        if (category == null) {
            throw new ResourceNotFoundException("Category not found");
        }
		Pageable pageable = PageRequest.of(page -1, size);
        Page<Story> stories = storyRepository.findByCategoryCode(categoryCode, pageable);
        return stories.map(StoriesDTO::transfer);
    }
    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
