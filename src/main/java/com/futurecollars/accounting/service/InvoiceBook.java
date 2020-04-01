package com.futurecollars.accounting.service;

import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.Database;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.mail.MessagingException;

public class InvoiceBook {

  private final Database database;
  Logger logger = LoggerFactory.getLogger(InvoiceBook.class);
  private final List<InvoiceBookObserver> observers;

  public InvoiceBook(Database database) {
    this.database = database;
    this.observers = new ArrayList<>();
  }

  public void registerObserver(InvoiceBookObserver observer) {
    observers.add(observer);
  }

  public Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException {
    if (invoice.getId() == null) {
      logger.info("Try to save invoice No.: {}", invoice.getInvoiceNumber());
    } else {
      logger.info("Try to update invoice id: {}", invoice.getId());
    }
    Invoice savedInvoice;
    try {
      savedInvoice = database.saveInvoice(invoice);
    } catch (DatabaseOperationException ex) {
      logger.error(String.valueOf(ex));
      throw ex;
    }
    logger.info("Invoice Mo. - {}, id - {} was saved successfully.",
        savedInvoice.getInvoiceNumber(),
        savedInvoice.getId());
    if (invoice.getId() == null) {
      observers.forEach(observer -> {
        try {
          observer.invoiceInserted(savedInvoice);
        } catch (MessagingException | DocumentException ex) {
          logger.warn(String.valueOf(ex));
        }
      });
    } else {
      observers.forEach(observer -> {
        try {
          observer.invoiceModified(savedInvoice);
        } catch (MessagingException | DocumentException ex) {
          logger.warn(String.valueOf(ex));
        }
      });
    }
    return savedInvoice;
  }

  public Invoice getInvoiceById(UUID id) throws DatabaseOperationException {
    logger.info("Try to get invoice id - {}", id);
    Invoice invoiceById = null;
    try {
      invoiceById = database.getInvoiceById(id);
    } catch (DatabaseOperationException ex) {
      logger.error(String.valueOf(ex));
      throw ex;
    }
    logger.info("Invoice id - {} has been read successfully", id);
    return invoiceById;
  }

  public List<Invoice> getInvoices() throws DatabaseOperationException {
    logger.info("Try to get all invoices.");
    List<Invoice> invoiceList = database.getInvoices();
    logger.info("{} invoices have been loaded.", invoiceList.size());
    return invoiceList;
  }

  public Invoice removeInvoiceById(UUID id) throws DatabaseOperationException {
    logger.info("Try to remove invoice id - {}", id);
    Invoice removedInvoice;
    try {
      removedInvoice = database.removeInvoiceById(id);
    } catch (DatabaseOperationException ex) {
      logger.error(String.valueOf(ex));
      throw ex;
    }
    logger.info("Invoice id - {}, was removed successfully.", id);
    observers.forEach(observer -> {
      try {
        observer.invoiceDeleted(removedInvoice);
      } catch (MessagingException | DocumentException ex) {
        logger.warn(String.valueOf(ex));
      }
    });
    return removedInvoice;
  }
}
