package com.futurecollars.accounting.infrastructure.database.hibernate;

import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.hibernate.model.InvoiceHibernate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CompanyMapper.class, InvoiceEntryMapper.class})
public interface InvoiceMapper {

  InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

  Invoice toInvoice(InvoiceHibernate invoiceHibernate);

  InvoiceHibernate toInvoiceHibernate(Invoice invoice);
}
