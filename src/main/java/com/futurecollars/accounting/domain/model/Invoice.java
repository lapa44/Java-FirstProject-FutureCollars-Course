package com.futurecollars.accounting.domain.model;

import com.futurecollars.accounting.service.InvoiceCalculateTotalValue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Invoice {

  private UUID id;
  private String invoiceNumber;
  private LocalDate date;
  private Company buyer;
  private Company seller;
  private List<InvoiceEntry> entries;
  public InvoiceCalculateTotalValue invoiceCalculateTotalValue;

  public Invoice(UUID id, String invoiceNumber, LocalDate date, Company buyer, Company seller,
                 List<InvoiceEntry> entries) {
    this.id = id;
    this.invoiceNumber = invoiceNumber;
    this.date = date;
    this.buyer = buyer;
    this.seller = seller;
    this.entries = new ArrayList<>();
    this.entries.addAll(entries);
    this.invoiceCalculateTotalValue = new InvoiceCalculateTotalValue();
  }

  public Invoice(Invoice invoice) {
    this.id = invoice.id;
    this.invoiceNumber = invoice.invoiceNumber;
    this.date = invoice.date;
    this.buyer = invoice.buyer;
    this.seller = invoice.seller;
    this.entries = invoice.entries;
    this.invoiceCalculateTotalValue = invoice.invoiceCalculateTotalValue;
  }

  public void addEntry(InvoiceEntry entry) {
    entries.add(entry);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
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

  @Override
  public boolean equals(Object ob) {
    if (this == ob) {
      return true;
    }
    if (ob == null || getClass() != ob.getClass()) {
      return false;
    }
    Invoice invoice = (Invoice) ob;
    return Objects.equals(id, invoice.id)
        && Objects.equals(invoiceNumber, invoice.invoiceNumber)
        && Objects.equals(date, invoice.date)
        && Objects.equals(buyer, invoice.buyer)
        && Objects.equals(seller, invoice.seller)
        && Objects.equals(entries, invoice.entries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, invoiceNumber, date, buyer, seller, entries);
  }
}
