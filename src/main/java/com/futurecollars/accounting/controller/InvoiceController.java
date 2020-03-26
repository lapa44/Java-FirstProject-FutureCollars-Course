package com.futurecollars.accounting.controller;

import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import com.futurecollars.accounting.service.InvoiceBook;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import com.futurecollars.accounting.service.PdfService;
import com.futurecollars.accounting.service.ZipService;
import com.itextpdf.text.DocumentException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/invoices")
@Api(value = "Invoice", description = "Menage your invoices.")
public class InvoiceController {

  private final InvoiceBook invoiceBook;

  public InvoiceController(InvoiceBook invoiceBook) {
    this.invoiceBook = invoiceBook;
  }

  @PostMapping
  @ApiOperation(value = "Saves and update invoices.")

  public ResponseEntity<Invoice> saveInvoice(@Valid @RequestBody Invoice invoice) {
    try {
      return new ResponseEntity<>(invoiceBook.saveInvoice(invoice), OK);
    } catch (DatabaseOperationException ex) {
      return new ResponseEntity<>(BAD_REQUEST);
    }
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Gets invoice by id.")
  @ApiImplicitParam(
      name = "id",
      value = "Put id of invoice.",
      dataType = "UUID",
      example = "a80496dd-3749-4d35-8d8d-78ce51b3ae75")
  @ApiResponses(value = {
      @ApiResponse(code = 404, message = "Invoice not found."),
      @ApiResponse(code = 200, message = "ok")})

  public ResponseEntity getInvoiceById(@Valid @RequestBody @PathVariable UUID id, @RequestHeader(
      name = "Accept", defaultValue = "application/json") String header) {
    try {
      Invoice invoiceById = invoiceBook.getInvoiceById(id);
      if (header.equals("application/pdf")) {
        ByteArrayResource pdfFile = new ByteArrayResource(
            PdfService.generateInvoiceFromPdf(invoiceById));
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "Invoice_no_"
                + invoiceById.getInvoiceNumber() + ".pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .contentLength(pdfFile.contentLength())
            .body(pdfFile);
      } else {
        return new ResponseEntity<>(invoiceById, OK);
      }
    } catch (DatabaseOperationException | DocumentException ex) {
      return new ResponseEntity<>(NOT_FOUND);
    }
  }

  @GetMapping
  @ApiOperation(value = "Gets all invoices.")
  public ResponseEntity getInvoices(@RequestHeader(value = "Accept",
      defaultValue = "application/json") String header) {
    List<Invoice> invoices;
    try {
      invoices = invoiceBook.getInvoices();
      if (header.equals("application/zip")) {
        ByteArrayResource zipFile = new ByteArrayResource(ZipService.getZipFromInvoices(invoices));
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=invoices.zip")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .contentLength(zipFile.contentLength())
            .body(zipFile);
      } else {
        return new ResponseEntity<>(invoices, OK);
      }
    } catch (DatabaseOperationException | IOException | DocumentException ex) {
      return new ResponseEntity<>(NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Removes invoice by id.")
  @ApiImplicitParam(
      name = "id",
      value = "Put id of invoice to remove.",
      dataType = "UUID",
      required = true,
      example = "a80496dd-3749-4d35-8d8d-78ce51b3ae75")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Invoice not found."),
      @ApiResponse(code = 204, message = "Invoice have been successfully removed.")})
  public ResponseEntity<Invoice> removeInvoiceById(@PathVariable UUID id) {
    try {
      return new ResponseEntity<>(invoiceBook.removeInvoiceById(id), NO_CONTENT);
    } catch (DatabaseOperationException ex) {
      return new ResponseEntity<>(BAD_REQUEST);
    }
  }
}
