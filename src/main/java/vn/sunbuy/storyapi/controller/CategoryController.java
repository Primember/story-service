package vn.sunbuy.storyapi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.sunbuy.storyapi.common.URI;
import vn.sunbuy.storyapi.entity.Category;
import vn.sunbuy.storyapi.model.CategoryDTO;
import vn.sunbuy.storyapi.model.StoriesDTO;
import vn.sunbuy.storyapi.service.CategoryService;
@RestController
@RequestMapping(URI.CATEGORY)
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@PostMapping(URI.CREATE)
	public Category createCategory(@RequestBody Category category) {
		return categoryService.createCategory(category);
	}
	@PutMapping(URI.UPDATE + URI.ID)
    public Category updateCategory(@RequestBody Category category,@PathVariable String id) {
		  return categoryService.updateCategory(category, id);
    }
	@GetMapping(URI.GETALL)
	public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }
	@DeleteMapping(URI.ID)
    public void deleteCategory(Category category,@PathVariable("id") String id) {
        categoryService.deleteCategory(id);
    }
	@GetMapping(URI.CATEGORYCODE)
	public ResponseEntity<Page<StoriesDTO>> getStoriesByCategoryCode(@PathVariable String categoryCode, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "16") int size) {
		Page<StoriesDTO> stories = categoryService.getStoriesByCategoryCode(categoryCode, page, size);
        return ResponseEntity.ok(stories);
    }
}
