package com.luu.ubas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.luu.ubas.service.AccountService;
import javax.validation.Valid;

@RestController
public class AccountController {

  // @PreAuthorize("#oauth2.hasScope('server') or #name.equals('demo')")
  // @RequestMapping(path = "/{name}", method = RequestMethod.GET)
  // public Account getAccountByName(@PathVariable String name) {
  // return accountService.findByName(name);
  // }

  // @RequestMapping(path = "/current", method = RequestMethod.GET)
  // public Account getCurrentAccount(Principal principal) {
  // return accountService.findByName(principal.getName());
  // }

  // @RequestMapping(path = "/current", method = RequestMethod.PUT)
  // public void saveCurrentAccount(Principal principal, @Valid @RequestBody Account account) {
  // accountService.saveChanges(principal.getName(), account);
  // }

  @PreAuthorize("#oauth2.hasRole('ROLE_SUPER') or #name.equals('demo')")
  @RequestMapping(path = "/test1", method = RequestMethod.GET)
  public String test1() {
    return "test1";
  }

  @RequestMapping(path = "/test2", method = RequestMethod.GET)
  public String test2() {
    return "test2";
  }
}
