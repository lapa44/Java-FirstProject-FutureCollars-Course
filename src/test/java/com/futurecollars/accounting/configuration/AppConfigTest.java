package com.futurecollars.accounting.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.futurecollars.accounting.infrastructure.database.Database;
import com.futurecollars.accounting.infrastructure.database.InMemoryDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("memory")
class AppConfigTest {

  @Autowired
  Database database;

  @Autowired
  private ApplicationContext context;

  @Test
  public void shouldLoadConfigContext() {
    context = new AnnotationConfigApplicationContext(AppConfig.class);
    assertNotNull(context);
  }

  @Test
  public void shouldLoadConfigContextForSpecifiedDatabase() {
    context = new AnnotationConfigApplicationContext(AppConfig.class);
    assertThat(database instanceof InMemoryDatabase).isTrue();
  }
}
