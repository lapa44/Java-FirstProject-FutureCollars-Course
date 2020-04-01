package com.futurecollars.accounting.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.futurecollars.accounting.domain.model.Invoice;
import com.itextpdf.text.DocumentException;

public class ZipService {

  public static byte[] getZipFromInvoices(List<Invoice> invoices)
      throws IOException, DocumentException {
    ByteArrayOutputStream zipByteStream = new ByteArrayOutputStream();
    ZipOutputStream zipOutputStream = new ZipOutputStream(zipByteStream);
    addZipEntries(zipOutputStream, invoices);
    zipOutputStream.close();
    return zipByteStream.toByteArray();
  }

  private static void addZipEntries(ZipOutputStream zipOutputStream, List<Invoice> invoices)
      throws DocumentException, IOException {
    for (Invoice invoice : invoices) {
      ZipEntry entry = new ZipEntry("Invoice_" + invoice.getInvoiceNumber() + ".pdf");
      byte[] pdf = PdfService.generateInvoiceFromPdf(invoice);
      entry.setSize(pdf.length);
      zipOutputStream.putNextEntry(entry);
      zipOutputStream.write(pdf);
      zipOutputStream.closeEntry();
    }
  }
}
