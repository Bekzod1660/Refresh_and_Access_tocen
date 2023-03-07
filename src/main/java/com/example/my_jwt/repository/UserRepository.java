package com.example.my_jwt.repository;

import com.example.my_jwt.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,Integer> {
   User findByUsername(String username);
}
