package com.futurecollars.accounting.service.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.service.pdf.PdfService;
import com.itextpdf.text.DocumentException;

public class MailFactory {

  private static final String PDF = "application/pdf";

  public static MimeMultipart createMailBody(Invoice invoice, MailMessage mailMessage)
      throws MessagingException, DocumentException {
    MimeMultipart mailBody = new MimeMultipart();
    mailBody.addBodyPart(createTextBodyPart(invoice, mailMessage));
    mailBody.addBodyPart(createAttachmentBodyPart(invoice, PDF));
    return mailBody;
  }

  private static MimeBodyPart createTextBodyPart(Invoice invoice, MailMessage mailMessage)
      throws MessagingException {
    MimeBodyPart textBodyPart = new MimeBodyPart();
    textBodyPart.setContent(mailMessage.getMessage().replace("Invoice",
        "Invoice no. " + invoice.getInvoiceNumber()), "text/html");
    return textBodyPart;
  }

  private static MimeBodyPart createAttachmentBodyPart(Invoice invoice, String type)
      throws MessagingException, DocumentException {
    byte[] bytes = PdfService.generateInvoiceFromPdf(invoice);
    DataSource dataSource = new ByteArrayDataSource(bytes, type);
    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
    attachmentBodyPart.setDataHandler(new DataHandler(dataSource));
    attachmentBodyPart.setFileName(String.format("Invoice_%s", invoice.getInvoiceNumber()));
    return attachmentBodyPart;
  }
}
