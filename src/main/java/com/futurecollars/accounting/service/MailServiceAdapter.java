package com.futurecollars.accounting.service;

import javax.mail.MessagingException;
import com.futurecollars.accounting.domain.model.Invoice;
import com.itextpdf.text.DocumentException;

public class MailServiceAdapter implements InvoiceBookObserver {

  private final MailService mailService;

  public MailServiceAdapter(MailService mailService) {
    this.mailService = mailService;
  }

  @Override
  public void invoiceInserted(Invoice invoice) throws MessagingException, DocumentException {
    mailService.sendMail(invoice, MailMessage.ADDED);
  }

  @Override
  public void invoiceModified(Invoice invoice) throws MessagingException, DocumentException {
    mailService.sendMail(invoice, MailMessage.MODIFIED);
  }

  @Override
  public void invoiceDeleted(Invoice invoice) throws MessagingException, DocumentException {
    mailService.sendMail(invoice, MailMessage.DELETED);
  }
}
