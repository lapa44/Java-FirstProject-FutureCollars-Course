package com.futurecollars.accounting.configuration;

import com.futurecollars.accounting.infrastructure.database.Database;
import com.futurecollars.accounting.infrastructure.database.HibernateDatabase;
import com.futurecollars.accounting.infrastructure.database.InFileDatabase;
import com.futurecollars.accounting.infrastructure.database.InMemoryDatabase;
import com.futurecollars.accounting.infrastructure.database.hibernate.InvoiceRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  @ConditionalOnProperty(name = "database", havingValue = "memory")
  public Database inMemoryDatabase() {
    return new InMemoryDatabase();
  }

  @Bean
  @ConditionalOnProperty(name = "database", havingValue = "inFile")
  public Database inFileDatabase() {
    return new InFileDatabase();
  }

  @Bean
  @ConditionalOnProperty(name = "database", havingValue = "hibernate")
  public Database hibernateDatabase(InvoiceRepository invoiceRepository) {
    return new HibernateDatabase(invoiceRepository);
  }
}
