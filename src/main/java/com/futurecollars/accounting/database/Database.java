package com.futurecollars.accounting.database;

import com.futurecollars.accounting.model.Invoice;

import java.util.List;
import java.util.UUID;

public interface Database {

    public void saveInvoice(Invoice invoice);
    public Invoice getInvoiceById(UUID id);
    public List<Invoice> getInvoices();
    public void updateInvoice(Invoice invoice);

}
