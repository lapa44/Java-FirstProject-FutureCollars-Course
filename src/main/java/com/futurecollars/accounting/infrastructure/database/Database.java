package com.futurecollars.accounting.infrastructure.database;

import com.futurecollars.accounting.domain.model.Invoice;

import java.util.List;
import java.util.UUID;

public interface Database {

  Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException;

  Invoice insertInvoice(Invoice invoice);

  Invoice updateInvoice(Invoice invoice) throws DatabaseOperationException;

  Invoice getInvoiceById(UUID id) throws DatabaseOperationException;

  List<Invoice> getInvoices();

  Invoice removeInvoice(Invoice invoice) throws DatabaseOperationException;

  boolean isInvoiceExists(Invoice invoice);
}
