package com.futurecollars.accounting.service;

import com.futurecollars.accounting.database.InMemoryDatabase;

public class InvoiceBook {

    private InMemoryDatabase database;

    public InvoiceBook() {
        this.database = new InMemoryDatabase();
    }
}
