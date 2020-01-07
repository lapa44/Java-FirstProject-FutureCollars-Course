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
  public synchronized Invoice saveInvoice(Invoice invoice) {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    if (!isInvoiceExists(invoice)) {
      return insertInvoice(invoice);
    } else {
      return updateInvoice(invoice);
    }
  }

  @Override
  public synchronized Invoice insertInvoice(Invoice invoice) {
    invoicesDatabase.add(invoice);
    return new Invoice(invoice);
  }

  @Override
  public synchronized Invoice updateInvoice(Invoice invoice) {
    return invoicesDatabase.set(invoicesDatabase.indexOf(invoice), invoice);
  }

  @Override
  public synchronized Invoice getInvoiceById(UUID id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null.");
    }
    for (Invoice e : invoicesDatabase) {
      if (id.equals(e.getId())) {
        return e;
      }
    }
    throw new DatabaseOperationException(
        new NoSuchElementException("Invoice of given ID was not found in database."));
  }

  @Override
  public synchronized List<Invoice> getInvoices() {
    return invoicesDatabase.stream().map(Invoice::new).collect(Collectors.toList());
  }

  @Override
  public synchronized Invoice removeInvoice(Invoice invoice) throws DatabaseOperationException {
    if (!isInvoiceExists(invoice)) {
      throw new DatabaseOperationException(
          new NoSuchElementException("Invoice of given ID was not found in database."));
    }
    return invoicesDatabase.remove(invoicesDatabase.indexOf(invoice));
  }

  @Override
  public boolean isInvoiceExists(Invoice invoice) {
    for (Invoice inv : invoicesDatabase) {
      if (inv.equals(invoice)) {
        return true;
      }
    }
    return false;
  }
}
