package com.futurecollars.accounting.configuration;

import com.futurecollars.accounting.infrastructure.database.Database;
import com.futurecollars.accounting.infrastructure.database.InFileDatabase;
import com.futurecollars.accounting.infrastructure.database.HibernateDatabase;
import com.futurecollars.accounting.infrastructure.database.InFileDatabase;
import com.futurecollars.accounting.infrastructure.database.InMemoryDatabase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.beans.factory.annotation.Value;
import com.futurecollars.accounting.infrastructure.database.hibernate.InvoiceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AppConfig {

  @Value("${database.filepath}")
  String filepath;


  @Bean
  @ConditionalOnProperty(name = "database", havingValue = "memory")
  public Database inMemoryDatabase() {
    return new InMemoryDatabase();
  }

  @Bean
  @ConditionalOnProperty(name = "database", havingValue = "inFile")
  public Database inFileDatabase() throws IOException {
    return new InFileDatabase(filepath);
  }

  @Bean
  @ConditionalOnProperty(name = "database", havingValue = "hibernate")
  public Database hibernateDatabase(InvoiceRepository invoiceRepository) {
    return new HibernateDatabase(invoiceRepository);
  }
}
