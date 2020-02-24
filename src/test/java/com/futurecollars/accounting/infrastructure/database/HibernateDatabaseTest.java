package com.futurecollars.accounting.infrastructure.database;

import com.futurecollars.accounting.infrastructure.database.hibernate.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HibernateDatabaseTest extends DatabaseTest {

  @Autowired
  InvoiceRepository invoiceRepository;

  @Override
  Database getDatabase() {
    return new HibernateDatabase(invoiceRepository);
  }
}
