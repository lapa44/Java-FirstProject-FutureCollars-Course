package com.futurecollars.accounting.soap.mapper;

import com.futurecollars.accounting.domain.model.InvoiceEntry;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InvoiceEntrySoapMapper {

  InvoiceEntrySoapMapper INSTANCE = Mappers.getMapper(InvoiceEntrySoapMapper.class);

  InvoiceEntry toInvoiceEntry(com.futurecollars.soap.InvoiceEntry invoiceEntrySoap);

  com.futurecollars.soap.InvoiceEntry toInvoiceEntrySoap(InvoiceEntry invoiceEntry);
}
