package com.luu.ubas.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.luu.ubas.models.Patient;

/**
 * 
 * @author titchip
 *
 */
public interface PatientRepository extends MongoRepository<Patient, String> {

  Patient findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Patient findByEmail(String email);

  void deleteByEmail(String email);
}
