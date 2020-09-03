package com.luu.ubas.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @PreAuthorize ("hasAnyAuthority('ROLE_SUPER')")
  @RequestMapping(path = "/test1", method = RequestMethod.GET)
  public String test1() {
    return "test1";
  }

  @PreAuthorize ("hasAnyAuthority('ROLE_USER')")
  @RequestMapping(path = "/test3", method = RequestMethod.GET)
  public String test3() {
    return "test3";
  }
  
  @RequestMapping(path = "/test2", method = RequestMethod.GET)
  public String test2() {
    return "test2";
  }
}
