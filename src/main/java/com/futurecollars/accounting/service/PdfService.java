package com.futurecollars.accounting.service;

import com.futurecollars.accounting.domain.model.Invoice;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

public class PdfService {

  private static Logger logger = LoggerFactory.getLogger(PdfService.class);

  public static byte[] generateInvoiceFromPdf(Invoice invoice) throws DocumentException {
    ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
    Document pdfDocument = new Document();
    PdfWriter.getInstance(pdfDocument, pdfStream);
    pdfDocument.open();
    pdfDocument.addTitle(String.format("Invoice no. %s", invoice.getInvoiceNumber()));
    pdfDocument.add(PdfFactory.getParagraphFromInvoice(invoice));
    pdfDocument.close();
    logger.info(String.format("Invoice no. %s  was exported to pdf file.",
        invoice.getInvoiceNumber()));
    return pdfStream.toByteArray();
  }
}
