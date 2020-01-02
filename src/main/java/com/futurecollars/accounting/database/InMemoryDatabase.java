package com.futurecollars.accounting.database;

import com.futurecollars.accounting.model.Invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class InMemoryDatabase implements Database {

    private List<Invoice> invoicesDatabase;

    public InMemoryDatabase() {
        this.invoicesDatabase = new ArrayList<>();
    }

    @Override
    public void saveInvoice(Invoice invoice) {
        invoicesDatabase.add(invoice);
    }

    @Override
    public Invoice getInvoiceById(UUID id) {
        for (Invoice e : invoicesDatabase) {
            if (id == e.getId()) {
                return e;
            }
        }
        throw new NoSuchElementException("Invoice of given ID was not found in database.");
    }

    @Override
    public List<Invoice> getInvoices() {
        return invoicesDatabase;
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        for (int i = 0; i < invoicesDatabase.size(); i++) {
            if (invoice.getId() == invoicesDatabase.get(i).getId()) {
                invoicesDatabase.set(i, invoice);
                return;
            }
        }
        throw new NoSuchElementException("Invoice of given ID was not found in database.");
    }
}
