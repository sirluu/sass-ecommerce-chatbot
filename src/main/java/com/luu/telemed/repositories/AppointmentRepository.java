package com.luu.telemed.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.luu.telemed.models.Appointment;


/**
 * 
 * @author HungLQ7130
 *
 */
@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
  List<Appointment> findByDoctorId(String doctorId);

  List<Appointment> findByPatientId(String patientId);

}

