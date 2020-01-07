package com.futurecollars.accounting.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceEntry {

  private String description;
  private String unit;
  private BigDecimal price;
  private BigDecimal vatValue;
  private Vat vatRate;

  public InvoiceEntry(String description, String unit, BigDecimal price, Vat vatRate) {
    this.description = description;
    this.unit = unit;
    this.price = price;
    this.vatRate = vatRate;
    this.vatValue = price.multiply(vatRate.getValue());
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getVatValue() {
    return vatValue;
  }

  public void setVatValue(BigDecimal vatValue) {
    this.vatValue = vatValue;
  }

  public Vat getVatRate() {
    return vatRate;
  }

  public void setVatRate(Vat vatRate) {
    this.vatRate = vatRate;
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

  public BigDecimal getPriceWithTax() {
    return price.add(vatValue);
  }
}
