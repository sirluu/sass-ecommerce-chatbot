package com.luu.telemed.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.luu.telemed.models.Photo;

public interface PhotoRepository extends MongoRepository<Photo, String> {

}
