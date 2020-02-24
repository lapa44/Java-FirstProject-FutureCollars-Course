package com.futurecollars.accounting.infrastructure.database.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "companies")
public class CompanyHibernate {

  @Id
  @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
      )
  @Column(name = "id", updatable = false, nullable = false)
  private UUID taxIdentificationNumber;
  private String address;
  private String name;

  public CompanyHibernate() {}

  public CompanyHibernate(UUID taxIdentificationNumber, String address, String name) {
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

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private UUID taxIdentificationNumber;
    private String address;
    private String name;

    private Builder() {}

    public Builder setTaxIdentificationNumber(UUID taxIdentificationNumber) {
      this.taxIdentificationNumber = taxIdentificationNumber;
      return this;
    }

    public Builder setAddress(String address) {
      this.address = address;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public CompanyHibernate build() {
      if (taxIdentificationNumber == null) {
        throw new IllegalStateException("Tax identification number cannot be null.");
      }
      if (address == null) {
        throw new IllegalStateException("Address cannot be null.");
      }
      if (name == null) {
        throw new IllegalStateException("Address cannot be null.");
      }
      return new CompanyHibernate(taxIdentificationNumber, address, name);
    }
  }
}
