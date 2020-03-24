package com.futurecollars.accounting.infrastructure.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.domain.model.DataGenerator;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.futurecollars.accounting.domain.model.Vat;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

abstract class DatabaseTest {

  abstract Database getDatabase() throws IOException;

  @RepeatedTest(3)
  void shouldSaveInvoiceToMemoryAndReturnItById()
      throws DatabaseOperationException, IOException {
    //given
    Database database = getDatabase();
    Invoice invoice = DataGenerator.randomInvoice().build();
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
      throws DatabaseOperationException, IOException {
    //given
    Database database = getDatabase();
    //when
    Invoice savedInvoiceBeforeUpdate = database
        .saveInvoice(DataGenerator.randomInvoice().build());
    Invoice savedInvoiceAfterUpdate = database.saveInvoice(DataGenerator.randomInvoice()
        .setId(savedInvoiceBeforeUpdate.getId())
        .build());
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
      throws DatabaseOperationException, IOException {
    //given
    Database database = getDatabase();
    Invoice invoice1 = DataGenerator.randomInvoice().build();
    Invoice invoice2 = DataGenerator.randomInvoice().build();
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
  void shouldThrowExceptionForNoInvoiceWhileGettingById() throws IOException {
    Database database = getDatabase();
    assertThrows(DatabaseOperationException.class, () ->
        database.getInvoiceById(UUID.randomUUID()));
  }

  @RepeatedTest(3)
  void shouldThrowExceptionForNullIdWhileGettingById() throws IOException {
    Database database = getDatabase();
    assertThrows(IllegalArgumentException.class,
        () -> database.getInvoiceById(null));
  }

  @RepeatedTest(3)
  void shouldThrowExceptionForNullInvoiceWhileSaving() throws IOException {
    Database database = getDatabase();
    assertThrows(IllegalArgumentException.class,
        () -> database.saveInvoice(null));
  }

  @RepeatedTest(3)
  void shouldRemoveInvoiceFromDatabase()
      throws DatabaseOperationException, IOException {
    //given
    Database database = getDatabase();
    //when
    Invoice savedInvoice = database
        .saveInvoice(DataGenerator.randomInvoice().build());
    //then
    assertEquals(1, database.getInvoices().size());
    Invoice invoiceRemoved = database
        .removeInvoiceById(savedInvoice.getId());
    assertEquals(savedInvoice, invoiceRemoved);
    assertEquals(0, database.getInvoices().size());
  }

  @RepeatedTest(3)
  void shouldThrowExceptionForNoInvoiceToRemove() throws IOException {
    Database database = getDatabase();
    assertThrows(DatabaseOperationException.class, () ->
        database.removeInvoiceById(UUID.randomUUID()));
  }

  @Test
  void shouldBuildInvoiceByBuilderAndAddItToDatabase()
      throws DatabaseOperationException, IOException {
    Database database = getDatabase();
    Invoice savedInvoice = database.saveInvoice(
        Invoice.builder()
            .setInvoiceNumber(" ")
            .setDate(LocalDate.now())
            .setBuyer(new Company(UUID.randomUUID(), " ", " "))
            .setSeller(new Company(UUID.randomUUID(), " ", " "))
            .addEntry(InvoiceEntry.builder()
                .setDescription("Cola")
                .setUnit("ml")
                .setQuantity(100)
                .setUnitPrice(new BigDecimal("3"))
                .setVatRate(Vat.VAT_0)
                .build())
            .build());
    assertNotNull(savedInvoice);
    assertNotNull(savedInvoice.getId());
    assertEquals(savedInvoice,
        database.getInvoiceById(savedInvoice.getId()));
    assertEquals(1, database.getInvoices().size());
  }
}
