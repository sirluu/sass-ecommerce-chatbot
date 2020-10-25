package com.luu.ubas.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.luu.ubas.models.Photo;

public interface PhotoRepository extends MongoRepository<Photo, String> {

}
