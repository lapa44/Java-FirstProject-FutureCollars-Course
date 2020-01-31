package com.futurecollars.accounting.infrastructure.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.futurecollars.accounting.domain.model.Invoice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class InFileDatabase implements Database {

  private final FileHelper fileHelper;
  private final ObjectMapper mapper;

  public InFileDatabase() {
    //todo path should be injected from properties file - Jonash springDI
    String path = "src\\main\\resources\\testFileDatabase.json";
    this.fileHelper = new FileHelper(path);
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

    if (!isInvoiceExists(invoice.getId())) {
      throw new DatabaseOperationException(
          new IllegalStateException("Fatal error"));
    } else {
      return updateInvoice(invoice);
    }
  }

  private synchronized Invoice insertInvoice(Invoice invoice)
      throws DatabaseOperationException {
    //todo missing validation of what?
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
    return new Invoice(invoiceToSave);
  }

  private synchronized Invoice updateInvoice(Invoice invoice)
      throws DatabaseOperationException {
    List<Invoice> listInvoicesFromFile = new ArrayList<>(getInvoices());
    for (int i = 0; i < listInvoicesFromFile.size(); i++) {
      if (listInvoicesFromFile.get(i).getId().equals(invoice.getId())) {
        listInvoicesFromFile.set(i, invoice);
        removeInvoiceById(invoice.getId());
        try {
          fileHelper.writeLineToFile(mapper
              .writeValueAsString(invoice));
        } catch (IOException ex) {
          throw new DatabaseOperationException(ex);
        }
        //todo There is the same invoice what has been passed as an input argument.
        // It's not good practice to return statement from the middle of the method.
        listInvoicesFromFile.add(invoice);
        return invoice;
      }
    }
    //todo Please describe what really happens in the error message.
    // If it is possible give tip what should be done to fix this issue.
    throw new DatabaseOperationException(
        new IllegalStateException("Fatal Error"));
  }

  @Override
  public synchronized Invoice getInvoiceById(UUID id)
      throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null.");
    }
    List<Invoice> objectInvoicesFromFile = new ArrayList<>(getInvoices());
    for (Invoice e : objectInvoicesFromFile) {
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

  private boolean isInvoiceExists(UUID id)
      throws DatabaseOperationException {
    List<Invoice> listInvoicesFromFile = new ArrayList<>(getInvoices());
    return listInvoicesFromFile.stream().map(Invoice::getId)
        .collect(Collectors.toList()).contains(id);
  }
}