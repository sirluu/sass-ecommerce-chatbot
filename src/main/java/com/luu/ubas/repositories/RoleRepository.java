package com.luu.ubas.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.luu.ubas.models.ERole;
import com.luu.ubas.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Role findByName(ERole name);

  void deleteByName(ERole name);
}
