package com.futurecollars.accounting.infrastructure.database;

import java.util.List;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.hibernate.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
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

  @BeforeEach
  public void clearDatabase() throws DatabaseOperationException {
    Database database = getDatabase();
    List<Invoice> ls = database.getInvoices();
    for (Invoice inv : ls) {
      database.removeInvoiceById(inv.getId());
    }
  }
}
