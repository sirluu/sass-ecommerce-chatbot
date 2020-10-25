package com.luu.ubas.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.luu.ubas.services.AdminService;
import com.luu.ubas.services.AppointmentService;
import com.luu.ubas.services.DoctorService;
import com.luu.ubas.services.PatientService;
import com.luu.ubas.services.PhotoService;
import com.luu.ubas.services.ReviewService;
import com.luu.ubas.services.RoleService;

@Order(1)
@Component
public class DBStartupRunner implements CommandLineRunner {

  Logger logger = LoggerFactory.getLogger(DBStartupRunner.class);

  @Autowired
  private DoctorService doctorService;

  @Autowired
  private PatientService patientService;

  @Autowired
  private AdminService adminService;


  @Autowired
  private ReviewService reviewService;

  @Autowired
  private PhotoService photoService;

  @Autowired
  private AppointmentService appointmentService;

  @Autowired
  private RoleService roleService;

  @Override
  public void run(String... args) throws Exception {
    logger.info(":::::::::::::::::::::::  DBStartupRunner");
    // logger.debug("deleting all database");
    // roleService.deleteAll();
    // photoService.deleteAll();
    // reviewService.deleteAll();
    // appointmentService.deleteAll();
    // adminService.deleteAll();
    // doctorService.deleteAll();
    // patientService.deleteAll();
  }

}
