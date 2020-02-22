package com.futurecollars.accounting.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class Company {

  private final UUID taxIdentificationNumber;
  private final String address;
  private final String name;

  public Company(UUID taxIdentificationNumber, String address,
      String name) {
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

  @Override
  public boolean equals(Object ob) {
    if (this == ob) {
      return true;
    }
    if (ob == null || getClass() != ob.getClass()) {
      return false;
    }
    Company company = (Company) ob;
    return Objects
        .equals(taxIdentificationNumber, company.taxIdentificationNumber)
        && Objects.equals(address, company.address)
        && Objects.equals(name, company.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taxIdentificationNumber, address, name);
  }
}
