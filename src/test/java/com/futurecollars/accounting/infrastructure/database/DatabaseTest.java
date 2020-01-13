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
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

abstract class DatabaseTest {
  abstract Database getDatabase();

  @Test
  void shouldSaveInvoiceToMemoryAndReturnItById() throws DatabaseOperationException {
    Database database = getDatabase();
    Invoice invoice = new Invoice(null, "No1", LocalDate.now(), new Company(
        UUID.randomUUID(), " ", " "), new Company(UUID.randomUUID(), " ",
        " "), Arrays.asList(new InvoiceEntry("Tequila", "Pln",
            new BigDecimal("20"),Vat.VAT_23), new InvoiceEntry("Cola", "Pln",
            new BigDecimal("5"), Vat.VAT_8)));
    Invoice savedInvoice = database.saveInvoice(invoice);
    assertNull(invoice.getId());
    assertNotNull(savedInvoice);
    assertNotNull(savedInvoice.getId());
    assertEquals(savedInvoice, database.getInvoiceById(savedInvoice.getId()));
    assertEquals(1, database.getInvoices().size());
  }

  @Test
  void shouldSaveAndUpdateInvoiceInMemory() throws DatabaseOperationException {
    Database database = getDatabase();
    Invoice savedInvoiceBeforeUpdate = database.saveInvoice(new Invoice(null, "No1",
        LocalDate.now(), new Company(UUID.randomUUID(), " ", " "), new Company(
            UUID.randomUUID(), " ", " "), Arrays.asList(new InvoiceEntry(
                "Tequila", "PLN", new BigDecimal("20"), Vat.VAT_23),
        new InvoiceEntry("Cola", "PLN", new BigDecimal("5"), Vat.VAT_8))));
    Invoice savedInvoiceAfterUpdate = database.saveInvoice(new Invoice(
        savedInvoiceBeforeUpdate.getId(), "No2", LocalDate.now(),
        new Company(UUID.randomUUID(), " ", " "),new Company(UUID.randomUUID(),
        " ", " "), Arrays.asList(new InvoiceEntry(
        "Beer", "Pln", new BigDecimal("7"), Vat.VAT_5))));
    assertNotNull(savedInvoiceBeforeUpdate);
    assertNotNull(savedInvoiceAfterUpdate);
    assertNotNull(savedInvoiceAfterUpdate.getId());
    assertNotEquals(savedInvoiceBeforeUpdate,
        database.getInvoiceById(savedInvoiceBeforeUpdate.getId()));
    assertEquals(savedInvoiceAfterUpdate,
        database.getInvoiceById(savedInvoiceBeforeUpdate.getId()));
    assertEquals(1, database.getInvoices().size());
  }

  @Test
  void shouldReturnAllInvoices() throws DatabaseOperationException {
    //given
    Database database = getDatabase();
    Invoice invoice1 = new Invoice(null, "No1", LocalDate.now(),
        new Company(UUID.randomUUID(), " ", " "), new Company(UUID.randomUUID(),
        " ", " "), Arrays.asList(
        new InvoiceEntry("Tequila", "PLN", new BigDecimal("20"), Vat.VAT_23),
        new InvoiceEntry("Cola", "PLN", new BigDecimal("5"), Vat.VAT_8)));
    Invoice invoice2 = new Invoice(null, "No1", LocalDate.now(),
        new Company(UUID.randomUUID(), " ", " "), new Company(UUID.randomUUID(),
        " ", " "), Arrays.asList(
        new InvoiceEntry("Sprite", "Pln", new BigDecimal("3.33"), Vat.VAT_23),
        new InvoiceEntry("Tea", "Pln", new BigDecimal("2.42"), Vat.VAT_8)));

    //when
    Invoice savedInvoice1 = database.saveInvoice(invoice1);
    Invoice savedInvoice2 = database.saveInvoice(invoice2);

    //then
    assertNull(invoice1.getId());
    assertNull(invoice2.getId());
    assertTrue(database.getInvoices().containsAll(Arrays.asList(savedInvoice1, savedInvoice2)));
    assertEquals(2, database.getInvoices().size());
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
    Invoice savedInvoice = database.saveInvoice(new Invoice(null, "No1", LocalDate.now(),
        new Company(UUID.randomUUID(), " ", " "), new Company(UUID.randomUUID(),
        " ", " "), Arrays.asList(
        new InvoiceEntry("Tequila", "PLN", new BigDecimal("20"), Vat.VAT_23),
        new InvoiceEntry("Cola", "PLN", new BigDecimal("5"), Vat.VAT_8))));
    assertEquals(1, database.getInvoices().size());
    assertEquals(savedInvoice, database.removeInvoiceById(savedInvoice.getId()));
    assertEquals(0, database.getInvoices().size());
  }

  @Test
  void shouldThrowExceptionForNoInvoiceToRemove() {
    Database database = getDatabase();
    assertThrows(DatabaseOperationException.class, () ->
        database.removeInvoiceById(UUID.randomUUID()));
  }

  @Test
  void shouldBuildInvoiceByBuilderAndAddItToDatabase() throws DatabaseOperationException {
    Database database = getDatabase();
    Invoice savedInvoice = database.saveInvoice(Invoice.builder()
        .setInvoiceNumber(" ")
        .setDate(LocalDate.now())
        .setBuyer(new Company(UUID.randomUUID(), " ", " "))
        .setSeller(new Company(UUID.randomUUID(), " ", " "))
        .addEntry(new InvoiceEntry("Cola", "PLN", new BigDecimal("5"), Vat.VAT_0))
        .build());
    assertNotNull(savedInvoice);
    assertNotNull(savedInvoice.getId());
    assertEquals(savedInvoice, database.getInvoiceById(savedInvoice.getId()));
    assertEquals(1, database.getInvoices().size());
  }
}
