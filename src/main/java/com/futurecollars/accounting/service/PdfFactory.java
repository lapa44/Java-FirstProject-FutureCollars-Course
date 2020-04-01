package com.futurecollars.accounting.service;

import java.util.List;
import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfFactory {

  private static Font BOLD = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
  private static Font REGULAR = new Font(FontFamily.TIMES_ROMAN, 12);

  public static Paragraph getParagraphFromInvoice(Invoice invoice) {
    Paragraph paragraph = new Paragraph();
    Anchor anchor = new Anchor("Invoice no. " + invoice.getInvoiceNumber(), BOLD);
    paragraph.add(anchor);
    paragraph.add(new Chunk(Chunk.NEWLINE));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.add(getFormattedDate(invoice.getDate().toString()));
    paragraph.add(new Chunk(Chunk.NEWLINE));
    paragraph.add(getTableFromCompanies(invoice.getBuyer(), invoice.getSeller()));
    paragraph.add(new Chunk(Chunk.NEWLINE));
    paragraph.add(getParagraphFromEntries(invoice.getEntries()));
    return paragraph;
  }

  private static PdfPTable getTableFromCompanies(Company buyer, Company seller) {
    PdfPTable table = new PdfPTable(2);
    table.setWidthPercentage(100);
    PdfPCell buyerCell = getCellFromCompany("Buyer:", buyer);
    table.addCell(buyerCell);
    PdfPCell sellerCell = getCellFromCompany("Seller:", seller);
    table.addCell(sellerCell);
    return table;
  }

  private static PdfPCell getCellFromCompany(String title, Company company) {
    PdfPCell cell = new PdfPCell();
    cell.setBorder(PdfPCell.NO_BORDER);
    cell.addElement(new Paragraph(title, BOLD));
    cell.addElement(new Paragraph(company.getName(), REGULAR));
    cell.addElement(new Paragraph(company.getAddress(), REGULAR));
    cell.addElement(new Paragraph(company.getTaxIdentificationNumber().toString(), REGULAR));
    return cell;
  }

  private static Paragraph getParagraphFromEntries(List<InvoiceEntry> entries) {
    PdfPTable pdfPTable = new PdfPTable(7);
    pdfPTable.setWidthPercentage(100);
    pdfPTable.setFooterRows(entries.size());

    pdfPTable.addCell(getCenteredCell("Description"));
    pdfPTable.addCell(getCenteredCell("Unit"));
    pdfPTable.addCell(getCenteredCell("Quantity"));
    pdfPTable.addCell(getCenteredCell("Unit Price"));
    pdfPTable.addCell(getCenteredCell("Price"));
    pdfPTable.addCell(getCenteredCell("Vat Value"));
    pdfPTable.addCell(getCenteredCell("Vat Rate"));

    for (InvoiceEntry entry : entries) {
      pdfPTable.addCell(new Phrase(entry.getDescription(), REGULAR));
      pdfPTable.addCell(new Phrase(entry.getUnit(), REGULAR));
      pdfPTable.addCell(new Phrase(entry.getQuantity().toString(), REGULAR));
      pdfPTable.addCell(new Phrase(entry.getUnitPrice().toString(), REGULAR));
      pdfPTable.addCell(new Phrase(entry.getPrice().toString(), REGULAR));
      pdfPTable.addCell(new Phrase(entry.getVatValue().toString(), REGULAR));
      pdfPTable.addCell(new Phrase(entry.getVatRate().toString(), REGULAR));
    }
    Paragraph paragraph = new Paragraph("Invoice Entries: ", BOLD);
    paragraph.add(pdfPTable);
    return paragraph;
  }

  private static PdfPCell getCenteredCell(String text) {
    PdfPCell cell = new PdfPCell(new Phrase(text, BOLD));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    return cell;
  }

  private static Paragraph getFormattedDate(String part2) {
    Paragraph paragraph = new Paragraph();
    paragraph.add(new Chunk("Date: ", BOLD));
    paragraph.add(new Chunk(part2, REGULAR));
    return paragraph;
  }

}
