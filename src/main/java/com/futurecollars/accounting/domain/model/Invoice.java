package com.futurecollars.accounting.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Invoice {

  private int localId;
  private UUID generalId;
  private LocalDate date;
  private Company buyer;
  private Company seller;
  private List<InvoiceEntry> entries;

  public Invoice(int localId, UUID generalId, LocalDate date, Company buyer, Company seller,
                 List<InvoiceEntry> entries) {
    this.localId = localId;
    this.generalId = generalId;
    this.date = date;
    this.buyer = buyer;
    this.seller = seller;
    this.entries = new ArrayList<>();
    this.entries.addAll(entries);
  }

  public Invoice(Invoice invoice) {
    this.localId = invoice.localId;
    this.generalId = invoice.generalId;
    this.date = invoice.date;
    this.buyer = invoice.buyer;
    this.seller = invoice.seller;
    this.entries = invoice.entries;
  }

  public BigDecimal getTotalValue() {
    return entries.stream()
        .map(InvoiceEntry::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public BigDecimal getTotalValueWithTaxes() {
    return entries.stream()
        .map(InvoiceEntry::getPriceWithTax)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public void addEntry(InvoiceEntry entry) {
    entries.add(entry);
  }

  public UUID getGeneralId() {
    return generalId;
  }

  public void setGeneralId(UUID generalId) {
    this.generalId = generalId;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Company getBuyer() {
    return buyer;
  }

  public void setBuyer(Company buyer) {
    this.buyer = buyer;
  }

  public Company getSeller() {
    return seller;
  }

  public void setSeller(Company seller) {
    this.seller = seller;
  }

  public List<InvoiceEntry> getEntries() {
    return entries;
  }

  public void setEntries(List<InvoiceEntry> entries) {
    this.entries = entries;
  }

  public int getLocalId() {
    return localId;
  }

  public void setLocalId(int localId) {
    this.localId = localId;
  }

  @Override
  public boolean equals(Object ob) {
    if (this == ob) {
      return true;
    }
    if (ob == null || getClass() != ob.getClass()) {
      return false;
    }
    Invoice invoice = (Invoice) ob;
    return Objects.equals(localId, invoice.localId)
        && Objects.equals(generalId, invoice.generalId)
        && Objects.equals(date, invoice.date)
        && Objects.equals(buyer, invoice.buyer)
        && Objects.equals(seller, invoice.seller)
        && Objects.equals(entries, invoice.entries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(localId, generalId, date, buyer, seller, entries);
  }
}
