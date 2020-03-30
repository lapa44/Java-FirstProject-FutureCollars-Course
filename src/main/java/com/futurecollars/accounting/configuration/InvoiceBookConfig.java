package com.futurecollars.accounting.configuration;

import com.futurecollars.accounting.service.InvoiceValidator;
import com.futurecollars.accounting.infrastructure.database.Database;
import com.futurecollars.accounting.service.InvoiceBook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceBookConfig {

  @Bean
  public InvoiceBook invoiceBook(Database database) {
    return new InvoiceBook(database);
  }

  @Bean
  public InvoiceValidator invoiceValidator(Database database) {
    return new InvoiceValidator(database);
  }
}
