package vn.sunbuy.storyapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import vn.sunbuy.storyapi.entity.Author;

public interface AuthorRepository extends MongoRepository<Author, String>{

}
