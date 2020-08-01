package com.luu.auth.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.luu.auth.domain.Test;
import com.luu.auth.domain.User;
import com.luu.auth.repository.TestRepository;
import com.luu.auth.repository.UserRepository;
import com.luu.auth.service.GenericService;

@Service
public class GenericServiceImpl implements GenericService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TestRepository testRepository;

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public List<User> findAllUsers() {
    return (List<User>) userRepository.findAll();
  }

  @Override
  public List<Test> findAllRandomCities() {
    return (List<Test>) testRepository.findAll();
  }
}
