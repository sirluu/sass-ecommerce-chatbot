package com.luu.ubas.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.luu.ubas.models.Appointment;


/**
 * cet interface hérite {@link MongoRepository MongoRepository.class}
 * 
 * @author Ayoub BenHaimoud<ayoubbenhaimoud@gmail.com>
 * @since 17-03-2020
 * 
 */
@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
  List<Appointment> findByDoctorId(String doctorId);

  List<Appointment> findByPatientId(String patientId);

}

