package com.futurecollars.accounting.service;

import com.futurecollars.accounting.infrastructure.database.Database;

class InvoiceBook {

  private final Database database;

  InvoiceBook(Database database) {
    this.database = database;
  }
}
