package com.futurecollars.accounting.controller;

import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.futurecollars.accounting.domain.model.Vat;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import com.futurecollars.accounting.service.InvoiceService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    InvoiceController(InvoiceService invoiceServicevice){
        this.invoiceService = invoiceServicevice;
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

    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable UUID id) {
        try {
            return invoiceService.getInvoiceById(id);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping
    public void create(@RequestBody Invoice invoice) {
        invoiceService.create(invoice);
    }


}
