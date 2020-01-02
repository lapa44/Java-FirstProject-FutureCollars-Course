package com.futurecollars.accounting.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Invoice {

    private UUID id;
    private Date date;
    private Company buyer;
    private Company seller;
    private List<InvoiceEntry> entries;

    public Invoice(UUID id, Date date, Company buyer, Company seller, List<InvoiceEntry> entries) {
        this.id = id;
        this.date = date;
        this.buyer = buyer;
        this.seller = seller;
        this.entries = new ArrayList<>();
        this.entries.addAll(entries);
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) &&
                Objects.equals(date, invoice.date) &&
                Objects.equals(buyer, invoice.buyer) &&
                Objects.equals(seller, invoice.seller) &&
                Objects.equals(entries, invoice.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, buyer, seller, entries);
    }
}
