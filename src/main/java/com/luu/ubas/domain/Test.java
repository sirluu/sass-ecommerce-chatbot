package com.luu.ubas.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "test")
public class Test {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "name")
  private String name;
  
  @Column(name = "subjectId")
  private Integer subjectId;
  
  @Column(name = "level")
  private Integer level;
  
  @Column(name = "status")
  private Integer status;
  
  @Column(name = "code")
  private String code;
}
