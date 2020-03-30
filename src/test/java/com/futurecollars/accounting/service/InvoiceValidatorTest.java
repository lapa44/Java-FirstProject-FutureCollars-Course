package com.futurecollars.accounting.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import com.futurecollars.accounting.domain.model.DataGenerator;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InvoiceValidatorTest {

  @Autowired
  InvoiceValidator invoiceValidator;

  @RepeatedTest(3)
  public void shouldValidateInvoice() throws DatabaseOperationException {
    Invoice invoice = DataGenerator.randomInvoice().build();

    assertThat(invoice).isNotNull();
    assertThat(invoiceValidator.isInvoiceValid(invoice)).isTrue();
  }

  @RepeatedTest(3)
  public void shouldNotValidateInvoice() throws DatabaseOperationException {
    Invoice invoice = new Invoice(
        null, null, null, null, null, new ArrayList<>()
    );

    assertThat(invoiceValidator.isInvoiceValid(invoice)).isFalse();
  }
}
