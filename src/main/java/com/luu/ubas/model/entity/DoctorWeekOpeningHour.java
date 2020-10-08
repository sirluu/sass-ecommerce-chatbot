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
public class DoctorWeekOpeningHour implements Serializable {

    private static final long serialVersionUID = 7698862379923111158L;

    private Long id;
    private String username;
    private String password;

}
