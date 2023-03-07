package com.example.my_jwt.repository;

import com.example.my_jwt.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,Integer> {

}
