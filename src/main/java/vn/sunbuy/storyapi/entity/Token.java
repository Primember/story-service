package vn.sunbuy.storyapi.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import vn.sunbuy.storyapi.common.JConstants.TokenType;

@Document(collection = "token")
@Getter
@Setter
public class Token {
	@Id
    private String id;
    private String token;
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    private User user;
}
