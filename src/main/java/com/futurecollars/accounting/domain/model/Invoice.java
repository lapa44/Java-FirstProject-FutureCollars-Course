package com.futurecollars.accounting.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public final class Invoice {

  private final UUID id;
  private final String invoiceNumber;
  private final LocalDate date;
  private final Company buyer;
  private final Company seller;
  private final List<InvoiceEntry> entries;

  public Invoice(UUID id, String invoiceNumber, LocalDate date, Company buyer, Company seller,
                 List<InvoiceEntry> entries) {
    this.id = id;
    this.invoiceNumber = invoiceNumber;
    this.date = date;
    this.buyer = buyer;
    this.seller = seller;
    this.entries = new ArrayList<>();
    this.entries.addAll(entries);
  }

  public Invoice(Invoice invoice) {
    this.id = invoice.id;
    this.invoiceNumber = invoice.invoiceNumber;
    this.date = invoice.date;
    this.buyer = invoice.buyer;
    this.seller = invoice.seller;
    this.entries = invoice.entries.stream().map(InvoiceEntry::new).collect(Collectors.toList());
  }

  public void addEntry(InvoiceEntry entry) {
    entries.add(entry);
  }

  public UUID getId() {
    return id;
  }

  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  public LocalDate getDate() {
    return date;
  }

  public Company getBuyer() {
    return buyer;
  }

  public Company getSeller() {
    return seller;
  }

  public List<InvoiceEntry> getEntries() {
    return entries.stream().map(InvoiceEntry::new).collect(Collectors.toList());
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

  private class InvoiceBuilder {
    //dorobic
  }
}
