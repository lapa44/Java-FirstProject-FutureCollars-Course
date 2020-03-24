package com.futurecollars.accounting.infrastructure.database.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import com.futurecollars.accounting.domain.model.Vat;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "entries")
public class InvoiceEntryHibernate {
  @Id
  @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
      )
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;
  private String description;
  private String unit;
  private Integer quantity;
  private BigDecimal unitPrice;
  private BigDecimal price;
  private BigDecimal vatValue;
  private Vat vatRate;

  public InvoiceEntryHibernate() {}

  public InvoiceEntryHibernate(UUID id, String description, String unit, Integer quantity,
                               BigDecimal unitPrice, Vat vatRate) {
    this.id = id;
    this.description = description;
    this.unit = unit;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.price = unitPrice.multiply(BigDecimal.valueOf(quantity));
    this.vatRate = vatRate;
    this.vatValue = price.multiply(vatRate.getValue());
  }

  public InvoiceEntryHibernate(InvoiceEntryHibernate invoiceEntry) {
    this.id = UUID.randomUUID();
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
  public int hashCode() {
    return Objects.hash(description, unit, quantity, unitPrice, price, vatValue, vatRate);
  }

  public BigDecimal getPriceWithTax() {
    return price.add(vatValue);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private UUID id;
    private String description;
    private String unit;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal price;
    private BigDecimal vatValue;
    private Vat vatRate;

    public Builder setId(UUID id) {
      this.id = id;
      return this;
    }

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

    public InvoiceEntryHibernate build() {
      if (description == null) {
        throw new IllegalStateException("Description cannot be null.");
      }
      if (unit == null) {
        throw new IllegalStateException("Unit cannot be null.");
      }
      if (quantity == null) {
        throw new IllegalStateException("Unit cannot be null.");
      }
      if (unitPrice == null) {
        throw new IllegalStateException("Unit price cannot be null");
      }
      if (vatRate == null) {
        throw new IllegalStateException("Vat rate cannot be null.");
      }
      return new InvoiceEntryHibernate(id, description, unit, quantity, unitPrice, vatRate);
    }
  }
}
