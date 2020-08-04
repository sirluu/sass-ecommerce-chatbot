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
public class ResourceController {
  @Autowired
  private GenericService service;

  @GetMapping(value = "/test")
  @PreAuthorize("hasAuthority('ADMIN_UBAS') or hasAuthority('USER_UBAS')")
  public List<Test> test() {
    return service.test();
  }

}
