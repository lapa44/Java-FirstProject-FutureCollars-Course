package com.futurecollars.accounting.controller;

import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.domain.model.DataGenerator;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.futurecollars.accounting.domain.model.Vat;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import com.futurecollars.accounting.service.InvoiceBook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class InvoiceControllerTest {

  @InjectMocks
  private InvoiceController controller;

  @Mock
  private InvoiceBook book;

  @Test
  public void shouldSaveInvoice() throws DatabaseOperationException {
    //given
    InvoiceBook book = mock(InvoiceBook.class);
    InvoiceController controller = new InvoiceController(book);
    Invoice invoice = DataGenerator.randomInvoice().build();

    //when
    controller.saveInvoice(invoice);
    //then
    verify(book).saveInvoice(invoice);
    assertNotNull(controller.saveInvoice(invoice));
  }

  @Test
  public void shouldReturnInvoiceById() throws DatabaseOperationException {
    //given
    InvoiceBook book = mock(InvoiceBook.class);
    InvoiceController controller = new InvoiceController(book);
    UUID id = UUID.randomUUID();
    Invoice invoice = DataGenerator.randomInvoice().build();

    //when
    controller.getInvoiceById(id);
    //then
    verify(book).getInvoiceById(id);
    assertNotNull(controller.saveInvoice(invoice));
    assertThat(controller.getInvoiceById(id).equals(invoice));
  }

  @Test
  public void shouldReturnInvoices() throws DatabaseOperationException {
    //given
    InvoiceBook book = mock(InvoiceBook.class);
    InvoiceController controller = new InvoiceController(book);
    UUID id = UUID.randomUUID();
    Invoice invoice = DataGenerator.randomInvoice().build();
    when(book.getInvoices()).thenReturn(Arrays.asList(
        new Invoice(invoice)));

    //when
    ResponseEntity<List<Invoice>> result = controller.getInvoices();
    //then
    verify(book).getInvoices();
    assertThat(result.equals(Arrays.asList()));
    assertThat(result.equals(book.getInvoices()));
    assertNotNull(result);
    assertEquals(1, book.getInvoices().size());
  }

  @Test
  public void shouldRemoveInvoiceById() throws DatabaseOperationException {
    //given
    InvoiceBook book = mock(InvoiceBook.class);
    InvoiceController controller = new InvoiceController(book);
    UUID id = UUID.randomUUID();

    //when
    ResponseEntity<Invoice> result = controller.removeInvoiceById(id);
    //then
    verify(book).removeInvoiceById(id);
    assertThat(result.equals(null));
    assertEquals(null, book.removeInvoiceById(id));
  }
}
