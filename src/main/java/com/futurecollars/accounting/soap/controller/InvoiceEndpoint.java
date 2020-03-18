package com.futurecollars.accounting.soap.controller;

import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import com.futurecollars.accounting.service.InvoiceBook;
import com.futurecollars.accounting.soap.mapper.InvoiceSoapMapper;
import com.futurecollars.soap.GetInvoiceRequest;
import com.futurecollars.soap.GetInvoiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.UUID;

//import com.futurecollars.soap.Invoice;

@Endpoint
public class InvoiceEndpoint {

  private static final String NAMESPACE_URI = "http://futurecollars.com/soap";

  @Autowired
  private InvoiceBook invoiceBook;

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceRequest")
  @ResponsePayload
  public GetInvoiceResponse getInvoice(@RequestPayload GetInvoiceRequest request) {
    GetInvoiceResponse response = new GetInvoiceResponse();

    UUID uuid = UUID.fromString(request.getId());
    try {
      Invoice invoiceById = invoiceBook.getInvoiceById(uuid);
      com.futurecollars.soap.Invoice soapInvoice = InvoiceSoapMapper
          .INSTANCE.toInvoiceSoap(invoiceById);
      response.setInvoice(soapInvoice);
    } catch (DatabaseOperationException ex) {
      ex.printStackTrace();
    }
    return response;
  }
}
