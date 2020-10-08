package com.luu.ubas.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Doctor implements Serializable {

    private static final long serialVersionUID = 8604990093149376515L;
    
    private String id;
    private Integer crmNumber;
    private Integer specializationId;
    private Integer medicalCareLocationId;
    private Double avgRating;

}
