package com.luu.telemed.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.luu.telemed.models.Review;

/**
 * 
 * @author titchip
 *
 */
public interface ReviewRepository extends MongoRepository<Review, String> {
}
