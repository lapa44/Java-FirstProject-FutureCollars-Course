package com.futurecollars.accounting.infrastructure.database;

import com.futurecollars.accounting.domain.model.Invoice;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface Database {

  Invoice saveInvoice(Invoice invoice) throws DatabaseOperationException;

  Invoice insertInvoice(Invoice invoice) throws DatabaseOperationException;

  Invoice updateInvoice(Invoice invoice) throws DatabaseOperationException;

  Invoice getInvoiceById(UUID id) throws DatabaseOperationException;

  List<Invoice> getInvoices() throws DatabaseOperationException;

  Invoice removeInvoiceById(UUID id) throws DatabaseOperationException;

  boolean isInvoiceExists(UUID id) throws DatabaseOperationException;
}
