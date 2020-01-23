package com.futurecollars.accounting.service;

import com.futurecollars.accounting.infrastructure.database.Database;

public class InvoiceBook {

  private final Database database;

  public InvoiceBook(Database database) {
    this.database = database;
  }

}
