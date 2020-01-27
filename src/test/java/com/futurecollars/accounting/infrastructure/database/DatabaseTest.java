package com.futurecollars.accounting.infrastructure.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.futurecollars.accounting.domain.model.Vat;
import org.junit.jupiter.api.RepeatedTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

abstract class DatabaseTest {

  abstract Database getDatabase();

  @RepeatedTest(3)
  void shouldSaveInvoiceToMemoryAndReturnItById()
      throws DatabaseOperationException {
    //given
    Database database = getDatabase();
    Invoice invoice = new Invoice(null, "No1",
        LocalDate.now(),
        new Company(
            UUID.randomUUID(), " ", " "),
        new Company(UUID.randomUUID(), " ",
            " "), Arrays.asList(
        new InvoiceEntry(
            "Tequila", "Pln",
            new BigDecimal("20"), Vat.VAT_23),
        new InvoiceEntry("Cola", "Pln",
            new BigDecimal("5"), Vat.VAT_8)));
    //when
    Invoice savedInvoice = database.saveInvoice(invoice);
    //then
    assertNull(invoice.getId());
    assertNotNull(savedInvoice);
    assertNotNull(savedInvoice.getId());
    assertEquals(savedInvoice,
        database.getInvoiceById(savedInvoice.getId()));
    assertEquals(1, database.getInvoices().size());
  }

  @RepeatedTest(3)
  void shouldSaveAndUpdateInvoiceInMemory()
      throws DatabaseOperationException {
    //given
    Database database = getDatabase();
    //when
    Invoice savedInvoiceBeforeUpdate = database
        .saveInvoice(new Invoice(null, "No1",
            LocalDate.now(), new Company(UUID.randomUUID(), " ", " "),
            new Company(
                UUID.randomUUID(), " ", " "),
            Arrays.asList(new InvoiceEntry(
                    "Tequila", "PLN", new BigDecimal("20"), Vat.VAT_23),
                new InvoiceEntry("Cola", "PLN", new BigDecimal("5"),
                    Vat.VAT_8))));
    Invoice savedInvoiceAfterUpdate = database.saveInvoice(
        new Invoice(
            savedInvoiceBeforeUpdate.getId(), "No2", LocalDate.now(),
            new Company(UUID.randomUUID(), " ", " "),
            new Company(UUID.randomUUID(),
                " ", " "), Arrays.asList(new InvoiceEntry(
            "Beer", "Pln", new BigDecimal("7"), Vat.VAT_5))));
    //then
    assertNotNull(savedInvoiceBeforeUpdate);
    assertNotNull(savedInvoiceAfterUpdate);
    assertNotNull(savedInvoiceAfterUpdate.getId());
    assertNotEquals(savedInvoiceBeforeUpdate,
        database.getInvoiceById(savedInvoiceBeforeUpdate.getId()));
    assertEquals(savedInvoiceAfterUpdate,
        database.getInvoiceById(savedInvoiceBeforeUpdate.getId()));
    assertEquals(1, database.getInvoices().size());
  }

  @RepeatedTest(3)
  void shouldReturnAllInvoices()
      throws DatabaseOperationException {
    //given
    Database database = getDatabase();
    Invoice invoice1 = new Invoice(null, "No1", LocalDate.now(),
        new Company(UUID.randomUUID(), " ", " "),
        new Company(UUID.randomUUID(),
            " ", " "), Arrays.asList(
        new InvoiceEntry("Beer01", "PLN", new BigDecimal("20"),
            Vat.VAT_23),
        new InvoiceEntry("Beer02", "PLN", new BigDecimal("5"),
            Vat.VAT_8)));
    Invoice invoice2 = new Invoice(null, "No1", LocalDate.now(),
        new Company(UUID.randomUUID(), " ", " "),
        new Company(UUID.randomUUID(),
            " ", " "), Arrays.asList(
        new InvoiceEntry("Whiskey01", "Pln", new BigDecimal("3.33"),
            Vat.VAT_23),
        new InvoiceEntry("whiskey02", "Pln", new BigDecimal("2.42"),
            Vat.VAT_8)));
    //when
    Invoice savedInvoice1 = database.saveInvoice(invoice1);
    Invoice savedInvoice2 = database.saveInvoice(invoice2);
    //then
    assertNull(invoice1.getId());
    assertNull(invoice2.getId());
    assertTrue(database.getInvoices()
        .containsAll(Arrays.asList(savedInvoice1, savedInvoice2)));
    assertEquals(2, database.getInvoices().size());
  }

  @RepeatedTest(3)
  void shouldThrowExceptionForNoInvoiceWhileGettingById() {
    Database database = getDatabase();
    assertThrows(DatabaseOperationException.class, () ->
        database.getInvoiceById(UUID.randomUUID()));
  }

  @RepeatedTest(3)
  void shouldThrowExceptionForNullIdWhileGettingById() {
    Database database = getDatabase();
    assertThrows(IllegalArgumentException.class,
        () -> database.getInvoiceById(null));
  }

  @RepeatedTest(3)
  void shouldThrowExceptionForNullInvoiceWhileSaving() {
    Database database = getDatabase();
    assertThrows(IllegalArgumentException.class,
        () -> database.saveInvoice(null));
  }

  @RepeatedTest(3)
  void shouldRemoveInvoiceFromDatabase()
      throws DatabaseOperationException {
    //given
    Database database = getDatabase();
    //when
    Invoice savedInvoice = database
        .saveInvoice(new Invoice(null, "No1", LocalDate.now(),
            new Company(UUID.randomUUID(), " ", " "),
            new Company(UUID.randomUUID(),
                " ", " "), Arrays.asList(
            new InvoiceEntry("Tequila", "PLN", new BigDecimal("20"),
                Vat.VAT_23),
            new InvoiceEntry("Cola", "PLN", new BigDecimal("5"),
                Vat.VAT_8))));
    //then
    assertEquals(1, database.getInvoices().size());
    Invoice invoiceRemoved = database
        .removeInvoiceById(savedInvoice.getId());
    assertEquals(savedInvoice, invoiceRemoved);
    assertEquals(0, database.getInvoices().size());
  }

  @RepeatedTest(3)
  void shouldThrowExceptionForNoInvoiceToRemove() {
    Database database = getDatabase();
    assertThrows(DatabaseOperationException.class, () ->
        database.removeInvoiceById(UUID.randomUUID()));
  }
}