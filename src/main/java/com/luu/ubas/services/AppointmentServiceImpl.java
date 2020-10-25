package com.luu.ubas.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.luu.ubas.models.Appointment;
import com.luu.ubas.repositories.AppointmentRepository;

/**
 * 
 * @author titchip
 *
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {
  @Autowired
  private AppointmentRepository appointmentRepository;

  @Override
  public Optional<List<Appointment>> findAll() {
    return Optional.of(appointmentRepository.findAll());
  }

  @Override
  public Optional<Appointment> findAppointmentById(String id) {
    return appointmentRepository.findById(id);
  }

  @Override
  public Optional<Appointment> insertAppointment(Appointment appointment) {
    return Optional.of(appointmentRepository.save(appointment));
  }

  @Override
  public Optional<Appointment> updateAppointment(Appointment appointment) {
    return Optional.of(appointmentRepository.save(appointment));
  }

  @Override
  public void deleteAppointmentById(String id) {
    appointmentRepository.deleteById(id);
  }

  @Override
  public Optional<List<Appointment>> findAppotByDoctorId(String doctorId) {
    return Optional.of(appointmentRepository.findByDoctorId(doctorId));
  }

  @Override
  public Optional<List<Appointment>> findAppotByPatientId(String patientId) {
    return Optional.of(appointmentRepository.findByPatientId(patientId));
  }

  @Override
  public void deleteAll() {
    appointmentRepository.deleteAll();
  }
}
