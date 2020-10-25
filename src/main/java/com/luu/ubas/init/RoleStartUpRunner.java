package com.luu.ubas.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.luu.ubas.models.ERole;
import com.luu.ubas.services.RoleService;

@Order(1)
@Component
public class RoleStartUpRunner implements CommandLineRunner {
  Logger logger = LoggerFactory.getLogger(RoleStartUpRunner.class);

  @Autowired
  RoleService roleService;

  @Override
  public void run(String... args) throws Exception {
    logger.info(":::::::::::::::::::::::  RoleStartUpRunner");
    /*
     * roleService.save(ERole.ROLE_USER); roleService.save(ERole.ROLE_ADMIN);
     * roleService.save(ERole.ROLE_DOCTOR); roleService.save(ERole.ROLE_PATIENT);
     * roleService.save(ERole.ROLE_MODERATOR); logger.info("all roles saved");
     */
  }

}
