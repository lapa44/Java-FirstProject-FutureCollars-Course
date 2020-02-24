package com.futurecollars.accounting.infrastructure.database.hibernate;

import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.futurecollars.accounting.infrastructure.database.hibernate.model.InvoiceEntryHibernate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InvoiceEntryMapper {

  InvoiceEntryMapper INSTANCE = Mappers.getMapper(InvoiceEntryMapper.class);

  InvoiceEntry toInvoiceEntry(InvoiceEntryHibernate invoiceEntryHibernate);

  InvoiceEntryHibernate toInvoiceEntryHibernate(InvoiceEntry invoiceEntry);
}
