package com.smartbalaram.auth.repository;

import com.smartbalaram.auth.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository to interact with MongoDB for User documents.
 */
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
