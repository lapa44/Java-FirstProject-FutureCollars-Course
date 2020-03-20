package com.futurecollars.accounting.soap.mapper;

import com.futurecollars.accounting.domain.model.InvoiceEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InvoiceEntrySoapMapper {

  InvoiceEntrySoapMapper INSTANCE = Mappers.getMapper(InvoiceEntrySoapMapper.class);

  @Mappings({
      @Mapping(target = "vatRate")})
  InvoiceEntry toInvoiceEntry(com.futurecollars.soap.InvoiceEntry invoiceEntrySoap);

  com.futurecollars.soap.InvoiceEntry toInvoiceEntrySoap(InvoiceEntry invoiceEntry);
}
