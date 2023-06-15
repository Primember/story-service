package vn.sunbuy.storyapi.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Document(collection = "categories")
public class Category {
	@Id
	private String id;
	@TextIndexed
	private String categoryName;
	@Indexed(unique = true)
	private String categoryCode;
	private String description;
	private String imageUrl;
	private String title;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
