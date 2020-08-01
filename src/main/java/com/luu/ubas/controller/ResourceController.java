package com.luu.ubas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luu.auth.domain.Test;
import com.luu.auth.domain.User;
import com.luu.auth.service.GenericService;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ResourceController {
  @Autowired
  private GenericService userService;

  @GetMapping(value = "/cities")
  @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
  public List<Test> getCities() {
    return userService.findAllRandomCities();
  }

}
