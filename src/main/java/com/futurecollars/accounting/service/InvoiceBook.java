package com.futurecollars.accounting.service;

import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.Database;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {

  private final Database database;

    InvoiceService(Database database) {

        this.database = database;
    }

  public Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException {
      return database.saveInvoice(invoice);
  }

  public Invoice insertInvoice(Invoice invoice){
      return database.insertInvoice(invoice);
  }

  public Invoice updateInvoice(Invoice invoice) throws DatabaseOperationException{
      return database.updateInvoice(invoice);
  }

  public Invoice getInvoiceById(UUID id) throws DatabaseOperationException{
      return database.getInvoiceById(id);
  }

  public List<Invoice> getInvoices(){
      return database.getInvoices();
  }

  public Invoice removeInvoiceById(UUID id) throws DatabaseOperationException{
      return database.removeInvoiceById(id);
  }

  public boolean doesInvoiceExist(UUID id){
    System.out.println("Someone asked if invoice with id " + id + " exists");
      return database.isInvoiceExists(id);
  }
}
