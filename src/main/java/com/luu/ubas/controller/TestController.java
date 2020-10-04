package com.luu.ubas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

//  @PreAuthorize ("hasAnyAuthority('ROLE_ADMIN')")
  @RequestMapping(path = "/test1", method = RequestMethod.GET)
  public String test1() {
    return "test1";
  }

//  @PreAuthorize ("hasAnyAuthority('ROLE_PATIENT')")
  @RequestMapping(path = "/test3", method = RequestMethod.GET)
  public String test3() {
    return "test3";
  }
  
  @RequestMapping(path = "/test2", method = RequestMethod.GET)
  public String test2() {
    return "test2";
  }
  
//  @PreAuthorize ("hasAnyAuthority('ROLE_DOCTOR')")
  @RequestMapping(path = "/test4", method = RequestMethod.GET)
  public String test4() {
    return "test4";
  }
}
