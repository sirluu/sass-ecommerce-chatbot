package com.luu.ubas.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1183385713216587274L;

    private Integer id;
    private Date date;
    private Timestamp startTime;
    private String doctorId;
    private String patientId;
    private String status;
    private String doctorDiagnosis;
    private String doctorNotes;
    private Double rating;
}
