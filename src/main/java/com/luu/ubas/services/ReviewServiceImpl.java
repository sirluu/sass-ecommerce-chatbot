package com.luu.ubas.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.luu.ubas.models.Review;
import com.luu.ubas.repositories.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

  @Autowired
  private ReviewRepository reviewRepository;

  @Override
  public Optional<Review> save(Review review) {
    return Optional.ofNullable(reviewRepository.save(review));
  }

  @Override
  public Optional<Review> findById(String id) {
    return reviewRepository.findById(id);
  }

  @Override
  public void deleteById(String id) {
    reviewRepository.deleteById(id);;
  }

  @Override
  public void deleteAll() {
    reviewRepository.deleteAll();
  }


}
