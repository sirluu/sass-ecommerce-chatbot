package com.luu.telemed.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.luu.telemed.models.ERole;
import com.luu.telemed.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Role findByName(ERole name);

  void deleteByName(ERole name);
}
