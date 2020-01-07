package com.futurecollars.accounting.infrastructure.database;

import com.futurecollars.accounting.domain.model.Invoice;

import java.util.List;
import java.util.UUID;

public interface Database {

  Invoice saveInvoice(Invoice invoice);

  Invoice insertInvoice(Invoice invoice);

  Invoice updateInvoice(Invoice invoice);

  Invoice getInvoiceById(UUID id) throws DatabaseOperationException;

  List<Invoice> getInvoices();

  Invoice removeInvoice(Invoice invoice) throws DatabaseOperationException;

  boolean isInvoiceExists(Invoice invoice);
}
