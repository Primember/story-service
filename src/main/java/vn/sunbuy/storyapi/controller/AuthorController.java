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
import vn.sunbuy.storyapi.entity.Author;
import vn.sunbuy.storyapi.entity.Story;
import vn.sunbuy.storyapi.service.AuthorService;
@RestController
@RequestMapping(URI.AUTHOR)
public class AuthorController {
	
	@Autowired
	 AuthorService authorService;
	@PostMapping(URI.CREATE)
	public Author createAuthor(@RequestBody Author author) {
	  return authorService.createAuthor(author);
	}
	@GetMapping(URI.GETSTORYBYAUTHORNAME)
	public ResponseEntity<Page<Story>> getStoryByAuthorName(@PathVariable String author, @RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(authorService.getStoriesByAuthor(author, page, size));
    }	
	
	@PutMapping(URI.UPDATE + URI.ID)
    public Author updateAuthor(@RequestBody Author author,@PathVariable String id) {
		  return authorService.updateAuthor(author, id);
    }
	@GetMapping(URI.GETALL)
	public List<Author> getAll() {
		return authorService.getAllAuthors();
	}
	@DeleteMapping(URI.ID)
    public void deleteAuthor(Author author,@PathVariable String id) {
        authorService.deleteAuthor(id);
    }
	
}
