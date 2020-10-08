package com.luu.ubas.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Address implements Serializable {
  
    private static final long serialVersionUID = 8604990093149376515L;
    
    private Integer id;
    private String uf;
    private String city;
    private String street;
    private Integer number;
}
