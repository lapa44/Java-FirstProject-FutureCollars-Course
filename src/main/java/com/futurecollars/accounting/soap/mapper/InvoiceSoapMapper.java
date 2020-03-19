package com.futurecollars.accounting.soap.mapper;

import com.futurecollars.accounting.domain.model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(uses = {CompanySoapMapper.class, InvoiceEntrySoapMapper.class},
    imports = UUID.class)
public interface InvoiceSoapMapper {

  InvoiceSoapMapper INSTANCE = Mappers.getMapper(InvoiceSoapMapper.class);

  @Mappings({
      @Mapping(target = "id", expression = "java(UUID.randomUUID())")})
  Invoice toInvoice(com.futurecollars.soap.Invoice invoiceSoap);

  @Mappings({
      @Mapping(target = "id", expression = "java(String.valueOf(invoice.getId()))")})
  com.futurecollars.soap.Invoice toInvoiceSoap(Invoice invoice);
}
