package com.futurecollars.accounting.service;

import javax.mail.MessagingException;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.Database;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InvoiceBook {

  private final Database database;
  private final List<InvoiceBookEvents> observers;

  public InvoiceBook(Database database) {
    this.database = database;
    this.observers = new ArrayList<>();
  }

  public void registerObserver(InvoiceBookEvents event) {
    observers.add(event);
  }

  public Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException {
    Invoice savedInvoice = database.saveInvoice(invoice);
    if (invoice.getId() == null) {
      observers.forEach(invoiceBookEvents -> {
        try {
          invoiceBookEvents.invoiceInsert(savedInvoice);
        } catch (MessagingException ex) {
          throw new RuntimeException(ex);
        }
      });
    } else {
      observers.forEach(invoiceBookEvents -> {
        try {
          invoiceBookEvents.invoiceModified(savedInvoice);
        } catch (MessagingException ex) {
          throw new RuntimeException(ex);
        }
      });
    }
    return savedInvoice;
  }

  public Invoice getInvoiceById(UUID id) throws DatabaseOperationException {
    return database.getInvoiceById(id);
  }

  public List<Invoice> getInvoices() throws DatabaseOperationException {
    return database.getInvoices();
  }

  public Invoice removeInvoiceById(UUID id) throws DatabaseOperationException {
    Invoice removedInvoice = database.removeInvoiceById(id);
    observers.forEach(invoiceBookEvents -> {
      try {
        invoiceBookEvents.invoiceDeleted(removedInvoice);
      } catch (MessagingException ex) {
        throw new RuntimeException(ex);
      }
    });
    return removedInvoice;
  }
}
