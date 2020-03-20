package com.futurecollars.accounting.soap.controller;

import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import com.futurecollars.accounting.service.InvoiceBook;
import com.futurecollars.accounting.soap.mapper.InvoiceSoapMapper;
import com.futurecollars.soap.GetInvoiceByIdRequest;
import com.futurecollars.soap.GetInvoiceByIdResponse;
import com.futurecollars.soap.InvoiceToSaveRequest;
import com.futurecollars.soap.InvoiceToSaveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.UUID;

@Endpoint
public class InvoiceEndpoint {

  private static final String NAMESPACE_URI = "http://futurecollars.com/soap";

  @Autowired
  private InvoiceBook invoiceBook;

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceByIdRequest")
  @ResponsePayload
  public GetInvoiceByIdResponse getInvoice(@RequestPayload GetInvoiceByIdRequest request) {
    GetInvoiceByIdResponse response = new GetInvoiceByIdResponse();

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

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "invoiceToSaveRequest")
  @ResponsePayload
  public InvoiceToSaveResponse saveInvoice(
      @RequestPayload InvoiceToSaveRequest request) {

    Invoice invoice = InvoiceSoapMapper.INSTANCE.toInvoice(request.getInvoice());
    InvoiceToSaveResponse response = new InvoiceToSaveResponse();

    try {
      invoiceBook.saveInvoice(invoice);
      response.setInvoice(request.getInvoice());
    } catch (DatabaseOperationException ex) {
      ex.printStackTrace();
      response.getErrorMsg().add("ERROR!");
    }
    return response;
  }
}
