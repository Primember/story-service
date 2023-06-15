package vn.sunbuy.storyapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import vn.sunbuy.storyapi.entity.Author;
import vn.sunbuy.storyapi.entity.Story;
import vn.sunbuy.storyapi.model.StoriesDTO;
import vn.sunbuy.storyapi.repository.AuthorRepository;
import vn.sunbuy.storyapi.repository.StoryRepository;
@Service
public class AuthorServiceIpml implements AuthorService {
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private StoryRepository storyRepository;
    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
 
    @Override
    public Author getAuthorById(String id) {
    	return authorRepository.findById(id).orElseThrow(() -> 
    	new ResourceNotFoundException(id));  
    }

    @Override
    public Author updateAuthor(Author author, String id) {
        Author _author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        _author.setName(author.getName());
        return authorRepository.save(_author);
    }
//	@Override
//	public Page<StoriesDTO> getStoriesByAuthor(String name, int page, int size) {
//		Author _author = authorRepository.findByName(name);
//		if(_author == null) {
//			throw new ResourceNotFoundException("Author not found");
//		}
//		Pageable pageable = PageRequest.of(page, size);
//		Page<Story> stories = storyRepository.findByAuthor(name, pageable);
//		return stories.map(StoriesDTO::transfer);
//	}
    @Override
    public void deleteAuthor(String id) {
        authorRepository.deleteById(id);
    }

	@Override
	public Author createAuthor(Author author) {
		return authorRepository.save(author);
	}

	@Override
	public Page<Story> getStoriesByAuthor(String author, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}


}