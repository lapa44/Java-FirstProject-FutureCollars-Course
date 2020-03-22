package com.futurecollars.accounting.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Objects;

public final class InvoiceEntry {

  private final String description;
  private final String unit;
  private final Integer quantity;
  private final BigDecimal unitPrice;
  private final BigDecimal price;
  private final BigDecimal vatValue;
  private final Vat vatRate;

  public InvoiceEntry(String description, String unit, Integer quantity, BigDecimal unitPrice,
                      Vat vatRate) {
    this.description = description;
    this.unit = unit;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.price = unitPrice.multiply(BigDecimal.valueOf(quantity));
    this.vatRate = vatRate;
    this.vatValue = price.multiply(vatRate.getValue());
  }

  public InvoiceEntry(InvoiceEntry invoiceEntry) {
    this.description = invoiceEntry.description;
    this.unit = invoiceEntry.unit;
    this.quantity = invoiceEntry.quantity;
    this.unitPrice = invoiceEntry.unitPrice;
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

  public Integer getQuantity() {
    return quantity;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
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
        && Objects.equals(quantity, that.quantity)
        && unitPrice.compareTo(that.unitPrice) == 0
        && price.compareTo(that.price) == 0
        && vatValue.compareTo(that.vatValue) == 0
        && vatRate == that.vatRate;
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, unit, quantity, unitPrice, price, vatValue, vatRate);
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
    private String unit;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal price;
    private BigDecimal vatValue;
    private Vat vatRate;

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder setUnit(String unit) {
      this.unit = unit;
      return this;
    }

    public Builder setQuantity(Integer quantity) {
      this.quantity = quantity;
      return this;
    }

    public Builder setUnitPrice(BigDecimal unitPrice) {
      this.unitPrice = unitPrice;
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
      if (quantity == null) {
        throw new IllegalStateException("Quantity cannot be null.");
      }
      if (unitPrice == null) {
        throw new IllegalStateException("Unit price cannot be null.");
      }
      if (vatRate == null) {
        throw new IllegalStateException("Vat rate cannot be null.");
      }
      return new InvoiceEntry(description, unit, quantity, unitPrice, vatRate);
    }
  }
}
