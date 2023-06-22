package vn.sunbuy.storyapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import vn.sunbuy.storyapi.entity.Token;

public interface TokenRepository extends MongoRepository<Token, String> {

  List<Token> findByUserIdAndExpiredOrRevokedFalse(String userId);

  Optional<Token> findByToken(String token);
}