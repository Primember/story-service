package vn.sunbuy.storyapi.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
@Document(collection = "author")
@Getter
@Setter
public class Author {
	
	@Id
	private String id;
	@TextIndexed
	private String name;
	
}
	