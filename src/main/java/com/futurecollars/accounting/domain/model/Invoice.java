package com.futurecollars.accounting.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

public final class Invoice {

  private final UUID id;
  @NotNull
  private final String invoiceNumber;
  @NotNull
  @PastOrPresent
  private final LocalDate date;
  @NotNull
  private final Company buyer;
  @NotNull
  private final Company seller;
  @NotNull
  private final List<InvoiceEntry> entries;

  @JsonCreator
  public Invoice(UUID id,
      String invoiceNumber,
      LocalDate date,
      Company buyer,
      Company seller,
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
    this.entries = invoice.entries.stream().map(InvoiceEntry::new)
        .collect(Collectors.toList());
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
    return entries.stream().map(InvoiceEntry::new)
        .collect(Collectors.toList());
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

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private UUID id;
    private String invoiceNumber;
    private LocalDate date;
    private Company buyer;
    private Company seller;
    private List<InvoiceEntry> entries;

    private Builder() {
    }

    public Builder setId(UUID id) {
      this.id = id;
      return this;
    }

    public Builder setInvoiceNumber(String invoiceNumber) {
      this.invoiceNumber = invoiceNumber;
      return this;
    }

    public Builder setDate(LocalDate date) {
      this.date = date;
      return this;
    }

    public Builder setBuyer(Company buyer) {
      this.buyer = buyer;
      return this;
    }

    public Builder setSeller(Company seller) {
      this.seller = seller;
      return this;
    }

    public Builder setEntries(List<InvoiceEntry> entries) {
      if (this.entries == null) {
        this.entries = new ArrayList<>();
      }
      this.entries.addAll(entries);
      return this;
    }

    public Builder addEntry(InvoiceEntry entry) {
      if (this.entries == null) {
        this.entries = new ArrayList<>();
      }
      this.entries.add(entry);
      return this;
    }

    public Builder clearEntries() {
      if (this.entries != null) {
        this.entries.clear();
      }
      return this;
    }

    public Invoice build() {
      if (invoiceNumber == null) {
        throw new IllegalStateException("Invoice number cannot be null.");
      }
      if (date == null) {
        throw new IllegalStateException("Invoice date cannot be null.");
      }
      if (buyer == null) {
        throw new IllegalStateException("Buyer cannot be null.");
      }
      if (seller == null) {
        throw new IllegalStateException("Seller cannot be null.");
      }
      if (entries.size() < 1) {
        throw new IllegalStateException("Invoice cannot have no entries.");
      }
      return new Invoice(id, invoiceNumber, date, buyer, seller, entries);
    }
  }
}
