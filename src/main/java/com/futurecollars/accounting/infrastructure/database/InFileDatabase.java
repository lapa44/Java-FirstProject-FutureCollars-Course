package com.futurecollars.accounting.infrastructure.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.futurecollars.accounting.domain.model.Invoice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

class InFileDatabase implements Database {

  private String path;
  private FileHelper fileHelper;
  private List<Invoice> invoicesDatabase;
  //  private List<Invoice> listInvoicesFromFile;
  private ObjectMapper mapper;

  public InFileDatabase() {
    this.path = "src\\main\\resources\\testFileDatabase.json";
    this.fileHelper = new FileHelper(path);
    this.invoicesDatabase = new ArrayList<>();
//    this.listInvoicesFromFile = new ArrayList<>();
    this.mapper = new ObjectMapper();
    this.mapper.registerModule(new JavaTimeModule());
    this.mapper.registerModule(new ParameterNamesModule());
    this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  @Override
  public synchronized Invoice saveInvoice(Invoice invoice)
      throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }

    if (invoice.getId() == null) {
      return insertInvoice(invoice);
    }

    if (isInvoiceExists(invoice.getId())) {
      return updateInvoice(invoice);
    } else {
      throw new DatabaseOperationException(
          new IllegalStateException("Fatal error"));
    }
  }

  @Override
  public synchronized Invoice insertInvoice(Invoice invoice)
      throws DatabaseOperationException {
    Invoice invoiceToSave = new Invoice(UUID.randomUUID(),
        invoice.getInvoiceNumber(), invoice.getDate(),
        invoice.getBuyer(), invoice.getSeller(), invoice.getEntries());
    try {
      String stringInvoiceToSaveToFile = mapper
          .writeValueAsString(invoiceToSave);
      fileHelper.writeLineToFile(stringInvoiceToSaveToFile);
    } catch (IOException ex) {
      throw new DatabaseOperationException(ex);
    }
    invoicesDatabase.add(invoiceToSave);
    return new Invoice(invoiceToSave);
  }

  @Override
  public synchronized Invoice updateInvoice(Invoice invoice)
      throws DatabaseOperationException {
    List<Invoice> listInvoicesFromFile = new ArrayList<>(getInvoices());
    for (int i = 0; i < listInvoicesFromFile.size(); i++) {
      //todo - czy tu nie powinna byc lista faktur z pliku? chyba nie
      //todo - chyba jednak tak bo musi zupdatowac invoice'a w pliku
//      if (invoicesDatabase.get(i).getId().equals(invoice.getId())) {
//        invoicesDatabase.set(i, invoice);
      //todo - powinno chyba byc tak, tylko jescze trzeba zaktualizowac plik,
      // czyli: zrobić metode w FileHelperze "updateinvoice",
      // albo wykorzystac usuwanie invoica i zapisanie invoice'a
      if (listInvoicesFromFile.get(i).getId().equals(invoice.getId())) {
        listInvoicesFromFile.set(i, invoice);
        //todo - tu sie wywala z tym remove, bo ponownie wchodzi
        // do isInvoiceExist i pobiera ponownie linie z pliku i dodaje
        // do listy, stąd sie bierze podówjna ilość. Trzeba chyba zrobić
        // metodę update w FileHelperze
        removeInvoiceById(invoice.getId());
        //todo - to bez sensu, trzeba by było na każdego invoice'a
        // wołac FileHelpera
        listInvoicesFromFile.add(invoice);
        //todo - to tez bez sensu jesli posiada juz Id
//        insertInvoice(invoice);
        return invoice;
      }
    }
    throw new DatabaseOperationException(
        new IllegalStateException("Fatal Error"));
  }

  @Override
  public synchronized Invoice getInvoiceById(UUID id)
      throws DatabaseOperationException {
    List<Invoice> objectInvoicesFromFile = new ArrayList<>(getInvoices());
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null.");
    }

    // todo zmienic aby sprawdzało invoice'y z pliku

    for (Invoice e : objectInvoicesFromFile/*invoicesDatabase*/) {
      if (id.equals(e.getId())) {
        return e;
      }
    }
    throw new DatabaseOperationException(
        new NoSuchElementException(
            "Invoice of given ID was not found in database."));
  }

  @Override
  public synchronized List<Invoice> getInvoices()
      throws DatabaseOperationException {
    List<String> invoicesFromFile;
    List<Invoice> objectInvoicesFromFile = new ArrayList<>();
    try {
      invoicesFromFile = new ArrayList<>(fileHelper.readLinesFromFile());
    } catch (IOException ex) {
      throw new DatabaseOperationException(ex);
    }
    try {
      for (String s : invoicesFromFile) {
        Invoice invoice = mapper
            .readValue(s, Invoice.class);
        objectInvoicesFromFile.add(invoice);
      }
    } catch (JsonProcessingException ex) {
      throw new DatabaseOperationException(ex);
    }
    return objectInvoicesFromFile;
  }

  public synchronized Invoice removeInvoiceById(UUID id)
      throws DatabaseOperationException {
    List<Invoice> listInvoicesFromFile = new ArrayList<>(getInvoices());
    if (!isInvoiceExists(id)) {
      throw new DatabaseOperationException(
          new NoSuchElementException(
              "Invoice of given ID was not found in database."));
    }
    for (int i = 0; i < listInvoicesFromFile.size(); i++) {
      if (listInvoicesFromFile.get(i).getId().equals(id)) {
        try {
          fileHelper.deleteLineFromFile(i);
        } catch (IOException ex) {
          throw new DatabaseOperationException(ex);
        }
        return listInvoicesFromFile.remove(i);
      }
    }
    throw new DatabaseOperationException(
        new IllegalStateException("Fatal Error"));
  }

  @Override
  public boolean isInvoiceExists(UUID id)
      throws DatabaseOperationException {
    List<Invoice> listInvoicesFromFile = new ArrayList<>(getInvoices());
    for (Invoice inv : listInvoicesFromFile) {
      if (inv.getId().equals(id)) {
        return true;
      }
    }
    return false;
  }
}