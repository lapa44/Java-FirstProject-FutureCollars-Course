package com.futurecollars.accounting.controller;

import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import com.futurecollars.accounting.service.InvoiceBook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceBook invoiceBook;

    public InvoiceController(InvoiceBook invoiceBook) {
        this.invoiceBook = invoiceBook;
    }

    @PostMapping
    public ResponseEntity<Invoice> saveInvoice(@Valid @RequestParam @RequestBody Invoice invoice) {
        try {
            return new ResponseEntity<>(invoiceBook.saveInvoice(invoice), OK);
        } catch (DatabaseOperationException ex) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@Valid @RequestBody @PathVariable UUID id) {
        try {
            Invoice invoiceById = invoiceBook.getInvoiceById(id);
            ResponseEntity<Invoice> invoiceResponseEntity = new ResponseEntity<>(invoiceById, OK);
            return invoiceResponseEntity;
        } catch (DatabaseOperationException ex) {
            return new ResponseEntity<>(NOT_FOUND);
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
            return new ResponseEntity<>(invoiceBook.removeInvoiceById(id), NO_CONTENT);
        } catch (DatabaseOperationException ex) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
    }
}
