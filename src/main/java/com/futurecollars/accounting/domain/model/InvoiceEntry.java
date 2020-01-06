package com.futurecollars.accounting.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceEntry {

  private String description;
  private BigDecimal price;
  private BigDecimal vatValue;
  private Vat vatRate;

  public InvoiceEntry(String description, BigDecimal price, Vat vatRate) {
    this.description = description;
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
        && Objects.equals(price, that.price)
        && Objects.equals(vatValue, that.vatValue)
        && vatRate == that.vatRate;
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, price, vatValue, vatRate);
  }

  public BigDecimal getPriceWithTax() {
    return price.add(vatValue);
  }
}
