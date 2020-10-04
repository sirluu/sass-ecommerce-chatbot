package com.luu.ubas.domain;

public enum RoleEnum {

  ROLE_ADMIN("ROLE_ADMIN"), ROLE_DOCTOR("ROLE_DOCTOR"), ROLE_PATIENT("ROLE_PATIENT");

  private String meaning;

  public String getMeaning() {
    return meaning;
  }

  RoleEnum() {}

  RoleEnum(String meaning) {
    this.meaning = meaning;
  }
}
