package com.luu.auth.service;

import java.util.List;
import com.luu.auth.domain.Test;
import com.luu.auth.domain.User;

public interface GenericService {
  User findByUsername(String username);

  List<User> findAllUsers();

  List<Test> findAllRandomCities();
}
