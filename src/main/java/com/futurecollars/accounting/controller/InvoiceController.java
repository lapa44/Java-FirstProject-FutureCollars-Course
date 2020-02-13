package com.futurecollars.accounting.controller;

import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import com.futurecollars.accounting.service.InvoiceBook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceBook invoiceBook;

    public InvoiceController(InvoiceBook invoiceBook) {
        this.invoiceBook = invoiceBook;
    }

    @PostMapping
    public ResponseEntity<Invoice> saveInvoice(@Valid @RequestBody Invoice invoice) {
        try {
            return new ResponseEntity<>(invoiceBook.saveInvoice(invoice), HttpStatus.OK);
        } catch (DatabaseOperationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Provide all invoice details.", ex);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@Valid @PathVariable UUID id) {
        try {
            return new ResponseEntity<>(invoiceBook.getInvoiceById(id), HttpStatus.OK);
        } catch (DatabaseOperationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Provide correct invoice id.", ex);
        }
    }

    @GetMapping
    public List<Invoice> getInvoices() {
        List<Invoice> invoices = invoiceBook.getInvoices();
        return invoices;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Invoice> removeInvoiceById(@PathVariable UUID id) {
        try {
            return new ResponseEntity<>(invoiceBook.removeInvoiceById(id), HttpStatus.NO_CONTENT);
        } catch (DatabaseOperationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Provide correct invoice id.", ex);
        }
    }
}
