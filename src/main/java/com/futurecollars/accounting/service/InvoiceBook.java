package com.futurecollars.accounting.service;

import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.Database;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InvoiceBook {

  private final Database database;

  public InvoiceBook(Database database) {
    this.database = database;
    }

  public Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException {
      return database.saveInvoice(invoice);
  }

  public Invoice getInvoiceById(UUID id) throws DatabaseOperationException{
      return database.getInvoiceById(id);
  }

  public List<Invoice> getInvoices() throws DatabaseOperationException {
      return database.getInvoices();
  }

  public Invoice removeInvoiceById(UUID id) throws DatabaseOperationException{
      return database.removeInvoiceById(id);
  }
}
