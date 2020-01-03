package com.futurecollars.accounting.infrastructure.database;

import com.futurecollars.accounting.domain.model.Invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

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
    if (findInvoiceById(invoice.getGeneralId()) == -1) {
      invoicesDatabase.add(invoice);
    } else {
      invoicesDatabase.set(findInvoiceById(invoice.getGeneralId()), invoice);
    }
    return new Invoice(invoice);
  }

  @Override
  public synchronized Invoice getInvoiceById(UUID id) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null.");
    }
    for (Invoice e : invoicesDatabase) {
      if (id == e.getGeneralId()) {
        return e;
      }
    }
    throw new NoSuchElementException("Invoice of given ID was not found in database.");
  }

  @Override
  public synchronized List<Invoice> getInvoices() {
    return new ArrayList<>(invoicesDatabase);
  }

  @Override
  public synchronized Invoice removeInvoice(Invoice invoice) {
    if (findInvoiceById(invoice.getGeneralId()) == -1) {
      throw new NoSuchElementException("Invoice of given ID was not found in database.");
    }
    return invoicesDatabase.remove(findInvoiceById(invoice.getGeneralId()));
  }

  @Override
  public synchronized int findInvoiceById(UUID id) {
    if (id == null) {
      throw new NullPointerException("Id cannot be null.");
    }
    if (invoicesDatabase.size() > 0) {
      for (int i = 0; i < invoicesDatabase.size(); i++) {
        if (invoicesDatabase.get(i).getGeneralId() == id) {
          return i;
        }
      }
    }
    return -1;
  }
}
