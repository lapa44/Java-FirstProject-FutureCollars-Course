package com.futurecollars.accounting.domain.model;

import java.util.UUID;

public class Company {

  private UUID taxIdentificationNumber;
  private String address;
  private String name;

  public Company(UUID taxIdentificationNumber, String address, String name) {
    this.taxIdentificationNumber = taxIdentificationNumber;
    this.address = address;
    this.name = name;
  }

  public UUID getTaxIdentificationNumber() {
    return taxIdentificationNumber;
  }

  public String getAddress() {
    return address;
  }

  public String getName() {
    return name;
  }
}
