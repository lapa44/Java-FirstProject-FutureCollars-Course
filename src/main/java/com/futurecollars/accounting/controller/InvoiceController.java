package com.futurecollars.accounting.controller;

import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.futurecollars.accounting.domain.model.Vat;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import com.futurecollars.accounting.service.InvoiceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RestController
@RequestMapping("/invoices")
public class InvoicesController {

    private final InvoiceService invoiceService;

    InvoicesController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
        Invoice invoice = new Invoice(null, "123", LocalDate.now(), new Company(UUID.randomUUID(), "32r23", "nazwa"), new Company(UUID.randomUUID(), "dfvb", "nazwa2"), Collections.singletonList(new InvoiceEntry("desp", "sztuka", new BigDecimal("2.25"), Vat.VAT_23)));
        try {
            invoiceService.saveInvoice(invoice);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    public Collection<Invoice> getInvoices() {
        return invoiceService.getInvoices();
    }

    @PostMapping
    public void createInvoice(@RequestBody Invoice invoice) {
        try {
            invoiceService.saveInvoice(invoice);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/{id}")
    public void updateInvoice(@RequestBody Invoice invoice, @PathVariable UUID id) {
        try {
            invoiceService.updateInvoice(invoice);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/{id}")
    public void removeInvoiceById(@PathVariable UUID id) {
        try {
            invoiceService.removeInvoiceById(id);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable UUID id) {
        try {
            return invoiceService.getInvoiceById(id);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        }
        return null;
    }
//    @GetMapping("/{id}")
//    public Invoice getInvoiceById(@PathVariable UUID id) {
//    Invoice invoice = invoiceService.getInvoiceById(id);
//        if (invoice != null) {
//        return invoice;
//    } else {
//        throw new DatabaseOperationException("Invoice not found.");
//    }
//}

    @GetMapping("/{dateRange}")
    public Collection<Invoice> getInvoicesByDateRange(@RequestParam @DateTimeFormat
            (pattern = "dd-MM-yyyy") LocalDate dateRange) {
//        @Temporal(TemporalType.DATE)
//        Date publicationDate;
//        List<Invoice> getInvoicesByDateRange(
//                Date publicationTimeStart,
//                Date publicationTimeEnd);
        return invoiceService.getInvoices();
    }
}
