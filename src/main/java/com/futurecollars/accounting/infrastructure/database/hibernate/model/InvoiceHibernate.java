package com.futurecollars.accounting.infrastructure.database.hibernate.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "invoices")
public class InvoiceHibernate {

  @Id
  @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
      )
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;
  @Column(name = "invoiceNumber")
  private String invoiceNumber;
  @Column(name = "date")
  private LocalDate date;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private CompanyHibernate buyer;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private CompanyHibernate seller;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "entries")
  private List<InvoiceEntryHibernate> entries;

  private InvoiceHibernate() {}

  public InvoiceHibernate(UUID id, String invoiceNumber, LocalDate date,CompanyHibernate buyer,
                          CompanyHibernate seller, List<InvoiceEntryHibernate> entries) {
    this.id = id;
    this.invoiceNumber = invoiceNumber;
    this.date = date;
    this.buyer = buyer;
    this.seller = seller;
    this.entries = new ArrayList<>();
    this.entries.addAll(entries);
  }

  public InvoiceHibernate(InvoiceHibernate invoice) {
    this.id = invoice.id;
    this.invoiceNumber = invoice.invoiceNumber;
    this.date = invoice.date;
    this.buyer = invoice.buyer;
    this.seller = invoice.seller;
    this.entries = invoice.entries.stream().map(InvoiceEntryHibernate::new)
        .collect(Collectors.toList());
  }

  public void addEntry(InvoiceEntryHibernate entry) {
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

  public CompanyHibernate getBuyer() {
    return buyer;
  }

  public CompanyHibernate getSeller() {
    return seller;
  }

  public List<InvoiceEntryHibernate> getEntries() {
    return entries.stream().map(InvoiceEntryHibernate::new).collect(Collectors.toList());
  }

  @Override
  public boolean equals(Object ob) {
    if (this == ob) {
      return true;
    }
    if (ob == null || getClass() != ob.getClass()) {
      return false;
    }
    InvoiceHibernate invoice = (InvoiceHibernate) ob;
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

  @Override
  public String toString() {
    return "InvoiceHibernate{" + "id=" + id + ", invoiceNumber='" + invoiceNumber + '\'' + ", date="
        + date + ", buyer=" + buyer + ", seller=" + seller + ", entries=" + entries + '}';
  }

  public static BuilderHibernate builder() {
    return new BuilderHibernate();
  }

  public static class BuilderHibernate {
    private UUID id;
    private String invoiceNumber;
    private LocalDate date;
    private CompanyHibernate buyer;
    private CompanyHibernate seller;
    private List<InvoiceEntryHibernate> entries;

    private BuilderHibernate() {
    }

    public BuilderHibernate setId(UUID id) {
      this.id = id;
      return this;
    }

    public BuilderHibernate setInvoiceNumber(String invoiceNumber) {
      this.invoiceNumber = invoiceNumber;
      return this;
    }

    public BuilderHibernate setDate(LocalDate date) {
      this.date = date;
      return this;
    }

    public BuilderHibernate setBuyer(CompanyHibernate buyer) {
      this.buyer = buyer;
      return this;
    }

    public BuilderHibernate setSeller(CompanyHibernate seller) {
      this.seller = seller;
      return this;
    }

    public BuilderHibernate setEntries(List<InvoiceEntryHibernate> entries) {
      if (this.entries == null) {
        this.entries = new ArrayList<>();
      }
      this.entries.addAll(entries);
      return this;
    }

    public BuilderHibernate addEntry(InvoiceEntryHibernate entry) {
      if (this.entries == null) {
        this.entries = new ArrayList<>();
      }
      this.entries.add(entry);
      return this;
    }

    public BuilderHibernate clearEntries() {
      if (this.entries != null) {
        this.entries.clear();
      }
      return this;
    }

    public InvoiceHibernate build() {
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
      return new InvoiceHibernate(id, invoiceNumber, date, buyer, seller, entries);
    }
  }
}
