package vn.sunbuy.storyapi.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import vn.sunbuy.storyapi.common.JConstants.ERole;
@Getter
@Setter
@Document(collection = "roles")
public class Role {
	
  @Id
  private String id;

  private ERole name;

  public Role() {

  }

}
