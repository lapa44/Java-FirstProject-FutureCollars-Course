package com.futurecollars.accounting.configuration;

import com.futurecollars.accounting.infrastructure.database.Database;
import com.futurecollars.accounting.infrastructure.database.InMemoryDatabase;
import com.futurecollars.accounting.service.InvoiceBook;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
  @Bean
  @ConditionalOnProperty(name = "database", havingValue = "inMemory")
  public Database inMemoryDatabase() {
    return new InMemoryDatabase();
  }

  @Bean
  @ConditionalOnProperty(name = "database", havingValue = "inFile")
  public Database inFileDatabase() {
    return null;
  }

  @Bean
  public InvoiceBook invoiceBook(final Database database) {
    return new InvoiceBook(database);
  }
}
