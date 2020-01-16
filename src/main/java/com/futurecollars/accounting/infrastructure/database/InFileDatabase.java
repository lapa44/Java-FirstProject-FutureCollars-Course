package com.futurecollars.accounting.infrastructure.database;


import com.futurecollars.accounting.domain.model.Invoice;

import java.util.List;
import java.util.UUID;

public class InFileDatabase implements Database {

  @Override
  public Invoice saveInvoice(Invoice invoice)
      throws DatabaseOperationException {
    return null;
  }

  @Override
  public Invoice insertInvoice(Invoice invoice) {
    return null;
  }

  @Override
  public Invoice updateInvoice(Invoice invoice)
      throws DatabaseOperationException {
    return null;
  }

  @Override
  public Invoice getInvoiceById(UUID id)
      throws DatabaseOperationException {
    return null;
  }

  @Override
  public List<Invoice> getInvoices() {
    return null;
  }

  @Override
  public Invoice removeInvoiceById(UUID id)
      throws DatabaseOperationException {
    return null;
  }

  @Override
  public boolean isInvoiceExists(UUID id) {
    return false;
  }
}
