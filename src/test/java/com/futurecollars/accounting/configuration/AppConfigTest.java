package com.futurecollars.accounting.configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = { AppConfig.class, InvoiceBookConfig.class } )
class AppConfigTest {

  @Autowired
  private ApplicationContext context;

  @Test
  public void shouldLoadConfigContext() {
    context = new AnnotationConfigApplicationContext(AppConfig.class);
    assertNotNull(context);
  }
}
