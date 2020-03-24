package com.futurecollars.accounting.service;

import javax.mail.MessagingException;
import com.futurecollars.accounting.domain.model.Invoice;

public interface InvoiceBookObserver {

  void invoiceInserted(Invoice invoice) throws MessagingException;

  void invoiceModified(Invoice invoice) throws MessagingException;

  void invoiceDeleted(Invoice invoice) throws MessagingException;
}