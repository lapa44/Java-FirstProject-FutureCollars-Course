package com.futurecollars.accounting.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;
import java.util.UUID;

@ApiModel(description = "Company data.")
public final class Company {

  @ApiModelProperty(position = 2, example = "8dc1499e-1e92-4cad-beae-7d6fe2d5318c")
  private final UUID taxIdentificationNumber;
  @ApiModelProperty(position = 1)
  private final String address;
  @ApiModelProperty(position = 0, example = "Company name")
  private final String name;

  public Company(UUID taxIdentificationNumber, String address, String name) {
    this.taxIdentificationNumber = taxIdentificationNumber;
    this.address = address;
    this.name = name;
  }

  @ApiModelProperty(required = true,
      dataType = "UUID",
      example = "2229b2e7-0a4e-4a7d-8e0b-c15774b80b71")
  public UUID getTaxIdentificationNumber() {
    return taxIdentificationNumber;
  }

  @ApiModelProperty(required = true)
  public String getAddress() {
    return address;
  }

  @ApiModelProperty(required = true)
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
    return Objects.equals(taxIdentificationNumber, company.taxIdentificationNumber)
        && Objects.equals(address, company.address)
        && Objects.equals(name, company.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taxIdentificationNumber, address, name);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private UUID taxIdentificationNumber;
    private String address;
    private String name;

    private Builder() {
    }

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

    public Company build() {
      if (taxIdentificationNumber == null) {
        throw new IllegalStateException("Tax identification number cannot be null.");
      }
      if (address == null) {
        throw new IllegalStateException("Address cannot be null.");
      }
      if (name == null) {
        throw new IllegalStateException("Name cannot be null.");
      }
      return new Company(taxIdentificationNumber, address, name);
    }
  }
}
