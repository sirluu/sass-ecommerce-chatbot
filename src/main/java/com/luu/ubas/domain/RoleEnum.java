package com.luu.ubas.domain;

public enum RoleEnum {

  ROLE_USER("ROLE_USER"),
  ROLE_ADMIN("ROLE_ADMIN"),
  ROLE_SUPER("ROLE_SUPER");

  private String meaning;

  public String getMeaning() {
    return meaning;
  }

  RoleEnum() {}

  RoleEnum(String meaning) {
    this.meaning = meaning;
  }
}
