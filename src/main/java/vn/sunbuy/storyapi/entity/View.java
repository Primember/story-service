package vn.sunbuy.storyapi.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

@Document(collection = "views")
public class View {
	
	@Id
	private String id;
	private LocalDate date;
	private Long totalView;
	private String storyId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	

}
