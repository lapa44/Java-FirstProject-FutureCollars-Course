package com.futurecollars.accounting.service;

import java.io.ByteArrayOutputStream;
import com.futurecollars.accounting.domain.model.Invoice;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfService {

  public static byte[] generateInvoiceFromPdf(Invoice invoice) throws DocumentException {
    ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
    Document pdfDocument = new Document();
    PdfWriter.getInstance(pdfDocument, pdfStream);
    pdfDocument.open();
    pdfDocument.addTitle(String.format("Invoice no. %s", invoice.getInvoiceNumber()));
    pdfDocument.add(PdfFactory.getParagraphFromInvoice(invoice));
    pdfDocument.close();
    return pdfStream.toByteArray();
  }

}
