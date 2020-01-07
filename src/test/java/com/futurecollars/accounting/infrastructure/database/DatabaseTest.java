package com.futurecollars.accounting.infrastructure.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.futurecollars.accounting.domain.model.Vat;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

abstract class DatabaseTest {
  abstract Database getDatabase();

  @Test
  void shouldSaveInvoiceToMemoryAndReturnItById() throws DatabaseOperationException {
    Database database = getDatabase();
    UUID id = UUID.randomUUID();
    Invoice invoice = new Invoice(id, "No1", LocalDate.now(), new Company(), new Company(),
        Arrays.asList(new InvoiceEntry("Tequila", "Pln", new BigDecimal("20"), Vat.VAT_23),
            new InvoiceEntry("Cola", "Pln", new BigDecimal("5"), Vat.VAT_8)));
    assertEquals(database.saveInvoice(invoice), database.getInvoiceById(id));
  }

  @Test
  void shouldSaveAndUpdateInvoiceInMemory() {
    Database database = getDatabase();
    Invoice invoice = database.saveInvoice(new Invoice(UUID.randomUUID(), "No1",
        LocalDate.now(), new Company(), new Company(), Arrays.asList(
        new InvoiceEntry("Tequila", "PLN", new BigDecimal("20"), Vat.VAT_23),
        new InvoiceEntry("Cola", "PLN", new BigDecimal("5"), Vat.VAT_8))));
    assertNotEquals(invoice, database.saveInvoice(new Invoice(invoice.getId(), "No2",
        LocalDate.now(), new Company(), new Company(), Arrays.asList(new InvoiceEntry(
            "Beer", "Pln", new BigDecimal("7"), Vat.VAT_5)))));
  }

  @Test
  void shouldReturnAllInvoices() {
    //given
    Database database = getDatabase();
    Invoice invoice = new Invoice(UUID.randomUUID(), "No1", LocalDate.now(),
        new Company(), new Company(), Arrays.asList(
        new InvoiceEntry("Tequila", "PLN", new BigDecimal("20"), Vat.VAT_23),
        new InvoiceEntry("Cola", "PLN", new BigDecimal("5"), Vat.VAT_8)));
    Invoice invoice2 = new Invoice(UUID.randomUUID(), "No1", LocalDate.now(),
        new Company(), new Company(), Arrays.asList(
        new InvoiceEntry("Sprite", "Pln", new BigDecimal("3.33"), Vat.VAT_23),
        new InvoiceEntry("Tea", "Pln", new BigDecimal("2.42"), Vat.VAT_8)));

    //when
    database.saveInvoice(invoice);
    List<Invoice> actual = database.getInvoices();
    database.saveInvoice(invoice2);


    //then
    assertNotEquals(actual, database.getInvoices());
  }

  @Test
  void shouldThrowExceptionForNoInvoiceWhileGettingById() {
    Database database = getDatabase();
    assertThrows(DatabaseOperationException.class, () ->
        database.getInvoiceById(UUID.randomUUID()));
  }

  @Test
  void shouldThrowExceptionForNullIdWhileGettingById() {
    Database database = getDatabase();
    assertThrows(IllegalArgumentException.class, () -> database.getInvoiceById(null));
  }

  @Test
  void shouldThrowExceptionForNullInvoiceWhileSaving() {
    Database database = getDatabase();
    assertThrows(IllegalArgumentException.class, () -> database.saveInvoice(null));
  }

  @Test
  void shouldRemoveInvoiceFromDatabase() throws DatabaseOperationException {
    Database database = getDatabase();
    Invoice invoice = new Invoice(UUID.randomUUID(), "No1", LocalDate.now(),
        new Company(), new Company(), Arrays.asList(
        new InvoiceEntry("Tequila", "PLN", new BigDecimal("20"), Vat.VAT_23),
        new InvoiceEntry("Cola", "PLN", new BigDecimal("5"), Vat.VAT_8)));
    database.saveInvoice(invoice);
    assertEquals(invoice, database.removeInvoice(invoice));
  }

  @Test
  void shouldThrowExceptionForNoInvoiceToRemove() {
    Database database = getDatabase();
    assertThrows(DatabaseOperationException.class, () -> database.removeInvoice(new Invoice(
        UUID.randomUUID(), "No1", LocalDate.now(), new Company(), new Company(), Arrays.asList(
        new InvoiceEntry("Tequila", "PLN", new BigDecimal("20"), Vat.VAT_23),
        new InvoiceEntry("Cola", "PLN", new BigDecimal("5"), Vat.VAT_8)))));
  }
}