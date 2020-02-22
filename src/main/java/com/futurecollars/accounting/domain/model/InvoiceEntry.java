package com.futurecollars.accounting.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Objects;

public final class InvoiceEntry {

  private final String description;
  private final String unit;
  private final BigDecimal price;
  private final BigDecimal vatValue;
  private final Vat vatRate;

  public InvoiceEntry(String description, String unit, BigDecimal price,
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

  public String getUnit() {
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
        && Objects.equals(price, that.price)
        && Objects.equals(vatValue, that.vatValue)
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
}
