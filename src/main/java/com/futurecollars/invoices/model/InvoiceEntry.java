package com.futurecollars.invoices.model;

import java.math.BigDecimal;

public class InvoiceEntry {

  private String productName;
  private String amount;
  private BigDecimal price;
  private Vat vat;

  public InvoiceEntry(String productName, String amount, BigDecimal price,
      Vat vat) {
    this.productName = productName;
    this.amount = amount;
    this.price = price;
    this.vat = vat;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Vat getVat() {
    return vat;
  }

  public void setVat(Vat vat) {
    this.vat = vat;
  }
}
