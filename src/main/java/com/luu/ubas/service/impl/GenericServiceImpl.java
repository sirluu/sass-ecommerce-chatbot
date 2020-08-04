package com.luu.ubas.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.luu.ubas.domain.Test;
import com.luu.ubas.repository.TestRepository;
import com.luu.ubas.service.GenericService;

@Service
public class GenericServiceImpl implements GenericService {

  @Autowired
  private TestRepository testRepository;

  @Override
  public List<Test> test() {
    return (List<Test>) testRepository.findAll();
  }
}
