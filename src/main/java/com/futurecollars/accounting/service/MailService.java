package com.futurecollars.accounting.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import com.futurecollars.accounting.domain.model.Invoice;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailService {

  @Autowired
  private JavaMailSender javaMailSender;

  @Value("${mail.admin}")
  private String recipient;

  public MailService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  public void sendMail(Invoice invoice, MailMessage mailMessage)
      throws MessagingException, DocumentException {
    MimeMessage message = createMail(invoice, mailMessage);
    javaMailSender.send(message);
  }

  private MimeMessage createMail(Invoice invoice, MailMessage mailMessage)
      throws MessagingException, DocumentException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
    mimeMessageHelper.setTo(recipient);
    mimeMessageHelper.setSubject(String.format("[InvoiceBook]: %s invoice no. %s",
        mailMessage.getTitlePart(), invoice.getInvoiceNumber()));
    mimeMessage.setContent(MailFactory.createMailBody(invoice, mailMessage));
    return mimeMessage;
  }
}
