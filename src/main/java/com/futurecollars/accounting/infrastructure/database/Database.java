package com.futurecollars.accounting.infrastructure.database;

import com.futurecollars.accounting.domain.model.Invoice;

import java.util.List;
import java.util.UUID;

public interface Database {

  public Invoice saveInvoice(Invoice invoice);

  public Invoice getInvoiceById(UUID id);

  public List<Invoice> getInvoices();

  public Invoice removeInvoice(Invoice invoice);

  public int findInvoiceById(UUID id);

}
