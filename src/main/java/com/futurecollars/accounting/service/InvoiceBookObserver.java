package com.futurecollars.accounting.service;

import javax.mail.MessagingException;
import com.futurecollars.accounting.domain.model.Invoice;
import com.itextpdf.text.DocumentException;

public interface InvoiceBookObserver {

  void invoiceInserted(Invoice invoice) throws MessagingException, DocumentException;

  void invoiceModified(Invoice invoice) throws MessagingException, DocumentException;

  void invoiceDeleted(Invoice invoice) throws MessagingException, DocumentException;
}
