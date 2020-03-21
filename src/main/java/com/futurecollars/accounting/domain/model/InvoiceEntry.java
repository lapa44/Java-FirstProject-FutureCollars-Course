package com.futurecollars.accounting.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Objects;

public final class InvoiceEntry {

  private final String description;
  private final Integer unit;
  private final BigDecimal price;
  private final BigDecimal vatValue;
  private final Vat vatRate;

  public InvoiceEntry(String description, Integer unit, BigDecimal price,
      Vat vatRate) {
    this.description = description;
    this.unit = unit;
    this.price = price;
    this.vatRate = vatRate;
    this.vatValue = price.multiply(vatRate.getValue());
  }

  public InvoiceEntry(InvoiceEntry invoiceEntry) {
    this.description = invoiceEntry.description;
    this.unit = invoiceEntry.unit;
    this.price = invoiceEntry.price;
    this.vatRate = invoiceEntry.vatRate;
    this.vatValue = invoiceEntry.vatValue;
  }

  public String getDescription() {
    return description;
  }

  public Integer getUnit() {
    return unit;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getVatValue() {
    return vatValue;
  }

  public Vat getVatRate() {
    return vatRate;
  }

  @Override
  public boolean equals(Object ob) {
    if (this == ob) {
      return true;
    }
    if (ob == null || getClass() != ob.getClass()) {
      return false;
    }
    InvoiceEntry that = (InvoiceEntry) ob;
    return Objects.equals(description, that.description)
        && Objects.equals(unit, that.unit)
        && price.compareTo(that.price) == 0
        && vatValue.compareTo(that.vatValue) == 0
        && vatRate == that.vatRate;
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, unit, price, vatValue, vatRate);
  }

  @JsonIgnore
  public BigDecimal getPriceWithTax() {
    return price.add(vatValue);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String description;
    private Integer unit;
    private BigDecimal price;
    private BigDecimal vatValue;
    private Vat vatRate;

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder setUnit(Integer unit) {
      this.unit = unit;
      return this;
    }

    public Builder setPrice(BigDecimal price) {
      this.price = price;
      return this;
    }

    public Builder setVatRate(Vat vatRate) {
      this.vatRate = vatRate;
      return this;
    }

    public InvoiceEntry build() {
      if (description == null) {
        throw new IllegalStateException("Description cannot be null.");
      }
      if (unit == null) {
        throw new IllegalStateException("Unit cannot be null.");
      }
      if (price == null) {
        throw new IllegalStateException("Price cannot be null.");
      }
      if (vatRate == null) {
        throw new IllegalStateException("Vat rate cannot be null.");
      }
      return new InvoiceEntry(description, unit, price, vatRate);
    }
  }
}
