package com.futurecollars.accounting.service;

import java.util.List;
import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.futurecollars.accounting.infrastructure.database.Database;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;

public class InvoiceValidator {

  private final Database database;

  public InvoiceValidator(Database database) {
    this.database = database;
  }

  public boolean isInvoiceValid(Invoice invoiceToSave) throws DatabaseOperationException {
    if (invoiceToSave.getId() == null) {
      for (Invoice invoice : database.getInvoices()) {
        if (invoice.getInvoiceNumber().equals(invoiceToSave.getInvoiceNumber())) {
          return false;
        }
      }
    }
    if (invoiceToSave.getDate() == null) {
      return false;
    }
    return isCompanyValid(invoiceToSave.getBuyer()) && isCompanyValid(invoiceToSave.getSeller())
        && areEntriesValid(invoiceToSave.getEntries());
  }

  private boolean isCompanyValid(Company company) {
    return company.getTaxIdentificationNumber() != null
        && company.getAddress() != null
        && company.getName() != null;
  }

  private boolean areEntriesValid(List<InvoiceEntry> entries) {
    for (InvoiceEntry entry : entries) {
      if (entry.getDescription() == null || entry.getQuantity() == null
          || entry.getUnit() == null || entry.getUnitPrice() == null
          || entry.getVatRate() == null) {
        return false;
      }
    }
    return true;
  }
}
