package com.luu.ubas.init;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.luu.ubas.repositories.AppointmentRepository;

/**
 * 
 * @author titchip
 *
 */
@Order(3)
@Component
public class AppointmentStartUpRunner implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(AppointmentStartUpRunner.class);

  @Autowired
  private AppointmentRepository appointmentRepository;


  public void run(String... args) throws Exception {
//    logger.info(
//        "Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C.",
//        Arrays.toString(args));
    logger.info(":::::::::::::::::::::::  AppointmentStartUpRunner");

    // supprimer les patientes
    // logger.info("removing all databases records");
    // appointmentRepository.deleteAll();


    // ------------------------------------------------------------------------------
    // Appointment appointment = new Appointment();
    // appointment.setDoctorId("doctorId");
    // appointment.setPatientId("patientId");
    // appointment.setAppointmentDate(new Date());
    // appointment.setBookingDate( null);
    // appointment.setDescription("i feel sickness !");
    // appointment.setState("PENGING");
    // Appointment appointment1 = appointmentRepository.save(appointment);
    // logger.debug("id: " +appointment1.getId());
    // logger.debug("appointment "+appointment1.getId() + "saved");
    // Appointment appointment2 = new Appointment();
    // appointment2.setDoctorId("doctorId2");
    // appointment2.setPatientId("patientId");
    // appointment2.setAppointmentDate(new Date());
    // appointment2.setBookingDate( null);
    // appointment2.setDescription("i feel sickness !");
    // appointment2.setState("PENDING");
    // appointment2 = appointmentRepository.save(appointment2);
    // logger.debug("id: " +appointment2.getId());
    // logger.debug("appointment "+appointment2.getId() + "saved");

    // ------------------------------------------------------------------------------
    // logger.info("appointments saved !");
    // logger.info("finished");
  }


}
