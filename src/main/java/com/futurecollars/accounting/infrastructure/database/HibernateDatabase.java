package com.futurecollars.accounting.infrastructure.database;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.hibernate.InvoiceMapper;
import com.futurecollars.accounting.infrastructure.database.hibernate.InvoiceRepository;
import com.futurecollars.accounting.infrastructure.database.hibernate.model.InvoiceHibernate;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateDatabase implements Database {

  @Autowired
  private InvoiceRepository invoiceRepository;

  public HibernateDatabase(InvoiceRepository invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
  }

  @Override
  public Invoice saveInvoice(Invoice invoice) {
    if (invoice == null) {
      throw new IllegalArgumentException("Invoice cannot be null.");
    }
    InvoiceHibernate invoiceHibernate = InvoiceMapper.INSTANCE.toInvoiceHibernate(invoice);
    if (invoiceHibernate.getId() == null) {
      return InvoiceMapper.INSTANCE.toInvoice(insertInvoice(invoiceHibernate));
    }
    return InvoiceMapper.INSTANCE.toInvoice(invoiceRepository.save(invoiceHibernate));
  }

  private InvoiceHibernate insertInvoice(InvoiceHibernate invoice) {
    return invoiceRepository.save(
        InvoiceHibernate.builder()
            .setId(UUID.randomUUID())
            .setInvoiceNumber(invoice.getInvoiceNumber())
            .setDate(invoice.getDate())
            .setBuyer(invoice.getBuyer())
            .setSeller(invoice.getSeller())
            .setEntries(invoice.getEntries())
            .build());
  }

  @Override
  public Invoice getInvoiceById(UUID id) throws DatabaseOperationException {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null.");
    }
    return InvoiceMapper.INSTANCE.toInvoice(invoiceRepository.findById(id)
        .orElseThrow(() -> new DatabaseOperationException(
            new NoSuchElementException("Invoice of given ID was not found in database."))));
  }

  @Override
  public List<Invoice> getInvoices() {
    List<Invoice> invoices = new ArrayList<>();
    invoiceRepository.findAll()
        .forEach((invoice) -> invoices.add(InvoiceMapper.INSTANCE.toInvoice(invoice)));
    return invoices;
  }

  @Override
  public Invoice removeInvoiceById(UUID id) throws DatabaseOperationException {
    InvoiceHibernate invoiceHibernate = invoiceRepository.findById(id)
        .orElseThrow(() -> new DatabaseOperationException(
            new NoSuchElementException("Invoice of given ID was not found in database.")));
    invoiceRepository.deleteById(id);
    return InvoiceMapper.INSTANCE.toInvoice(invoiceHibernate);
  }
}
