package com.luu.ubas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.luu.ubas.models.Doctor;

/**
 * 
 * @author titchip
 *
 */
@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String> {

  @Override
  Optional<Doctor> findById(String id);

  @Query("{ 'clinic.city' : { $regex: ?0 , '$options' : 'i'} }")
  List<Doctor> searchByCity(String query, Sort sort);

  // $text: { $search: "clinic" }
  @Query("{$text: { $search: ?0 }, 'clinic.city': {$regex: ?1 , '$options' : 'i'} }")
  List<Doctor> search(String query, String city, Sort sort);

  Doctor findByEmail(String email);

  Doctor findByUsername(String username);

  boolean existsByEmail(String Email);

  boolean existsByUsername(String username);

  void deleteByEmail(String email);

}
