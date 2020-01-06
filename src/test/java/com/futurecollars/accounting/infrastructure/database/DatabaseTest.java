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

abstract class DatabaseTest {
  abstract Database getDatabase();

  @Test
  void shouldSaveInvoiceToMemoryAndReturnItById() {
    //given
    Database database = getDatabase();
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
    Database database = getDatabase();
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
    Database database = getDatabase();
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
    Database database = getDatabase();
    assertThrows(NoSuchElementException.class, () -> database.getInvoiceById(UUID.randomUUID()));
  }

  @Test
  void shouldThrowExceptionForNullGeneralIdWhileGettingById() {
    Database database = getDatabase();
    assertThrows(IllegalArgumentException.class, () -> database.getInvoiceById(null));
  }

  @Test
  void shouldThrowExceptionForNullInvoiceWhileSaving() {
    Database database = getDatabase();
    assertThrows(IllegalArgumentException.class, () -> database.saveInvoice(null));
  }

  @Test
  void shouldRemoveInvoiceFromDatabase() {
    Database database = getDatabase();
    Invoice invoice = new Invoice(1, UUID.randomUUID(), LocalDate.now(), new Company(),
        new Company(), Arrays.asList(
        new InvoiceEntry("Tequila", new BigDecimal("20"), Vat.VAT_23),
        new InvoiceEntry("Cola", new BigDecimal("5"), Vat.VAT_8)));
    database.saveInvoice(invoice);
    assertEquals(invoice, database.removeInvoice(invoice));
  }

  @Test
  void shouldThrowExceptionForNoInvoiceToRemove() {
    Database database = getDatabase();
    assertThrows(NoSuchElementException.class, () -> database.removeInvoice(new Invoice(1,
        UUID.randomUUID(), LocalDate.now(), new Company(), new Company(), Arrays.asList(
        new InvoiceEntry("Tequila", new BigDecimal("20"), Vat.VAT_23),
        new InvoiceEntry("Cola", new BigDecimal("5"), Vat.VAT_8)))));
  }

  @Test
  void shouldThrowExceptionForNullIdWhileFindingInvoice() {
    Database database = getDatabase();
    assertThrows(NullPointerException.class, () -> database.findInvoiceById(null));
  }
}