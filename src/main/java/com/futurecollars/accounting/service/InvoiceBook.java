package com.futurecollars.accounting.service;

import javax.mail.MessagingException;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.Database;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import com.itextpdf.text.DocumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InvoiceBook {

  private final Database database;
  private final List<InvoiceBookObserver> observers;

  public InvoiceBook(Database database) {
    this.database = database;
    this.observers = new ArrayList<>();
  }

  public void registerObserver(InvoiceBookObserver observer) {
    observers.add(observer);
  }

  public Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException {
    Invoice savedInvoice = database.saveInvoice(invoice);
    if (invoice.getId() == null) {
      observers.forEach(observer -> {
        try {
          observer.invoiceInserted(savedInvoice);
        } catch (MessagingException | DocumentException ex) {
          // logger.warn(String.valueOf(ex);
        }
      });
    } else {
      observers.forEach(observer -> {
        try {
          observer.invoiceModified(savedInvoice);
        } catch (MessagingException | DocumentException ex) {
          // logger.warn(String.valueOf(ex);
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
    observers.forEach(observer -> {
      try {
        observer.invoiceDeleted(removedInvoice);
      } catch (MessagingException | DocumentException ex) {
        // logger.warn(String.valueOf(ex);
      }
    });
    return removedInvoice;
  }
}
