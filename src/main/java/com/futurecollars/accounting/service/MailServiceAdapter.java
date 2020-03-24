package com.futurecollars.accounting.service;

import javax.mail.MessagingException;
import com.futurecollars.accounting.domain.model.Invoice;

public class MailServiceAdapter implements InvoiceBookObserver {

  private final MailService mailService;

  public MailServiceAdapter(MailService mailService) {
    this.mailService = mailService;
  }

  @Override
  public void invoiceInserted(Invoice invoice) throws MessagingException {
    mailService.sendMail(invoice, MailMessage.ADDED);
  }

  @Override
  public void invoiceModified(Invoice invoice) throws MessagingException {
    mailService.sendMail(invoice, MailMessage.MODIFIED);
  }

  @Override
  public void invoiceDeleted(Invoice invoice) throws MessagingException {
    mailService.sendMail(invoice, MailMessage.DELETED);
  }
}
