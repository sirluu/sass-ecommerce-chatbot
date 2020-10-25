package com.luu.ubas.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.luu.ubas.models.Admin;

/**
 * 
 * @author titchip
 *
 */
@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {

  Admin findByEmail(String email);

  Admin findByUsername(String username);

  boolean existsByEmail(String Email);

  boolean existsByUsername(String username);

  void deleteByEmail(String email);

}
