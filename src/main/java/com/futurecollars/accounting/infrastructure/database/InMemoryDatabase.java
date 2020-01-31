package com.futurecollars.accounting.infrastructure.database;

import com.futurecollars.accounting.domain.model.Invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

class InMemoryDatabase implements Database {

  private List<Invoice> invoicesDatabase;

  public InMemoryDatabase() {
    this.invoicesDatabase = new ArrayList<>();
  }

  @Override
  public synchronized Invoice saveInvoice(Invoice invoice)
      throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    if (invoice.getId() == null) {
      return insertInvoice(invoice);
    }
    if (isInvoiceExists(invoice.getId())) {
      return updateInvoice(invoice);
    } else {
      throw new DatabaseOperationException(
          new IllegalStateException("Fatal error"));
    }
  }

  private synchronized Invoice insertInvoice(Invoice invoice) {
    Invoice invoiceToSave = new Invoice(UUID.randomUUID(),
        invoice.getInvoiceNumber(),
        invoice.getDate(), invoice.getBuyer(), invoice.getSeller(),
        invoice.getEntries());
    invoicesDatabase.add(invoiceToSave);
    return new Invoice(invoiceToSave);
  }

  private synchronized Invoice updateInvoice(Invoice invoice)
      throws DatabaseOperationException {
    for (int i = 0; i < invoicesDatabase.size(); i++) {
      if (invoicesDatabase.get(i).getId().equals(invoice.getId())) {
        invoicesDatabase.set(i, invoice);
        return invoice;
      }
    }
    throw new DatabaseOperationException(
        new IllegalStateException("Fatal Error"));
  }

  @Override
  public synchronized Invoice getInvoiceById(UUID id)
      throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null.");
    }
    for (Invoice e : invoicesDatabase) {
      if (id.equals(e.getId())) {
        return e;
      }
    }
    throw new DatabaseOperationException(
        new NoSuchElementException(
            "Invoice of given ID was not found in database."));
  }

  @Override
  public synchronized List<Invoice> getInvoices() {
    return invoicesDatabase.stream().map(Invoice::new)
        .collect(Collectors.toList());
  }

  @Override
  public synchronized Invoice removeInvoiceById(UUID id)
      throws DatabaseOperationException {
    if (!isInvoiceExists(id)) {
      throw new DatabaseOperationException(
          new NoSuchElementException(
              "Invoice of given ID was not found in database."));
    }
    for (int i = 0; i < invoicesDatabase.size(); i++) {
      if (invoicesDatabase.get(i).getId().equals(id)) {
        return invoicesDatabase.remove(i);
      }
    }
    throw new DatabaseOperationException(
        new IllegalStateException("Fatal Error"));
  }

  private boolean isInvoiceExists(UUID id) {
    for (Invoice inv : invoicesDatabase) {
      if (inv.getId().equals(id)) {
        return true;
      }
    }
    return false;
  }
}
