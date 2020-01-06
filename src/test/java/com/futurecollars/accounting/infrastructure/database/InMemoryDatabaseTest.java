package com.futurecollars.accounting.infrastructure.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.futurecollars.accounting.domain.model.Vat;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.UUID;

class InMemoryDatabaseTest {

  @Test
  void shouldSaveInvoiceToMemoryAndReturnItById() {
    //given
    InMemoryDatabase database = new InMemoryDatabase();
    UUID id = UUID.randomUUID();
    Invoice invoice = new Invoice(1, id, LocalDate.now(), new Company(), new Company(),
        Arrays.asList(new InvoiceEntry("Tequila", new BigDecimal("20"), Vat.VAT_23),
            new InvoiceEntry("Cola", new BigDecimal("5"), Vat.VAT_8)));

    //when
    database.saveInvoice(invoice);

    //then
    assertEquals(invoice, database.getInvoiceById(id));
  }

  @Test
  void shouldSaveAndUpdateInvoiceInMemory() {
    InMemoryDatabase database = new InMemoryDatabase();
    Invoice invoice = database.saveInvoice(new Invoice(1, UUID.randomUUID(), LocalDate.now(),
        new Company(), new Company(), Arrays.asList(
        new InvoiceEntry("Tequila", new BigDecimal("20"), Vat.VAT_23),
        new InvoiceEntry("Cola", new BigDecimal("5"), Vat.VAT_8))));
    invoice.setLocalId(2);
    assertEquals(invoice, database.saveInvoice(invoice));
  }

  @Test
  void shouldReturnAllInvoices() {
    //given
    InMemoryDatabase database = new InMemoryDatabase();
    Invoice invoice = new Invoice(1, UUID.randomUUID(), LocalDate.now(), new Company(),
        new Company(), Arrays.asList(
        new InvoiceEntry("Tequila", new BigDecimal("20"), Vat.VAT_23),
        new InvoiceEntry("Cola", new BigDecimal("5"), Vat.VAT_8)));
    Invoice invoice2 = new Invoice(2, UUID.randomUUID(), LocalDate.now(), new Company(),
        new Company(), Arrays.asList(
        new InvoiceEntry("Sprite", new BigDecimal("3.33"), Vat.VAT_23),
        new InvoiceEntry("Tea", new BigDecimal("2.42"), Vat.VAT_8)));

    //when
    database.saveInvoice(invoice);
    database.saveInvoice(invoice2);

    //then
    assertEquals(Arrays.asList(invoice, invoice2), database.getInvoices());
  }

  @Test
  void shouldThrowExceptionForNoInvoiceWhileGettingById() {
    InMemoryDatabase database = new InMemoryDatabase();
    assertThrows(NoSuchElementException.class, () -> database.getInvoiceById(UUID.randomUUID()));
  }

  @Test
  void shouldThrowExceptionForNullGeneralIdWhileGettingById() {
    InMemoryDatabase database = new InMemoryDatabase();
    assertThrows(IllegalArgumentException.class, () -> database.getInvoiceById(null));
  }

  @Test
  void shouldThrowExceptionForNullInvoiceWhileSaving() {
    InMemoryDatabase database = new InMemoryDatabase();
    assertThrows(IllegalArgumentException.class, () -> database.saveInvoice(null));
  }

  @Test
  void shouldRemoveInvoiceFromDatabase() {
    InMemoryDatabase database = new InMemoryDatabase();
    Invoice invoice = new Invoice(1, UUID.randomUUID(), LocalDate.now(), new Company(),
        new Company(), Arrays.asList(
        new InvoiceEntry("Tequila", new BigDecimal("20"), Vat.VAT_23),
        new InvoiceEntry("Cola", new BigDecimal("5"), Vat.VAT_8)));
    database.saveInvoice(invoice);
    assertEquals(invoice, database.removeInvoice(invoice));
  }

  @Test
  void shouldThrowExceptionForNoInvoiceToRemove() {
    InMemoryDatabase database = new InMemoryDatabase();
    assertThrows(NoSuchElementException.class, () -> database.removeInvoice(new Invoice(1,
        UUID.randomUUID(), LocalDate.now(), new Company(), new Company(), Arrays.asList(
        new InvoiceEntry("Tequila", new BigDecimal("20"), Vat.VAT_23),
        new InvoiceEntry("Cola", new BigDecimal("5"), Vat.VAT_8)))));
  }

  @Test
  void shouldThrowExceptionForNullIdWhileFindingInvoice() {
    InMemoryDatabase database = new InMemoryDatabase();
    assertThrows(NullPointerException.class, () -> database.findInvoiceById(null));
  }
}
