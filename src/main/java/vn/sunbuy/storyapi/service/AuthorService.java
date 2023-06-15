package vn.sunbuy.storyapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.sunbuy.storyapi.entity.Author;
import vn.sunbuy.storyapi.entity.Story;
@Service
public interface AuthorService {
	Author createAuthor(Author author);
	List<Author> getAllAuthors();
    Author getAuthorById(String id);
    Author updateAuthor(Author author, String id);
	public Page<Story> getStoriesByAuthor(String author, int page, int size);
    void deleteAuthor(String id);
}
