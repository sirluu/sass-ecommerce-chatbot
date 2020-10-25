package com.luu.ubas.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import com.luu.ubas.models.Review;

/**
 * 
 * @author titchip
 *
 */
public interface ReviewRepository extends MongoRepository<Review, String> {
}
