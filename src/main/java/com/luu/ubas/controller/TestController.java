package com.luu.ubas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luu.ubas.domain.Test;
import com.luu.ubas.service.GenericService;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TestController {
  @Autowired
  private GenericService service;

  @GetMapping(value = "/test1")
  @PreAuthorize("hasAuthority('ROLE_SUPER') or hasAuthority('ROLE_ADMIN')")
  public List<Test> test1() {
    return service.test();
  }
  @GetMapping(value = "test2")
  public String test2() {
    return "Ok done!";
  }
}
