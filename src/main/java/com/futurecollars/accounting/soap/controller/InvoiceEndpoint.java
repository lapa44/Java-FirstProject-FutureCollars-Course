package com.futurecollars.accounting.soap.controller;

import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import com.futurecollars.accounting.service.InvoiceBook;
import com.futurecollars.accounting.soap.mapper.InvoiceSoapMapper;
import com.futurecollars.soap.GetAllInvoicesRequest;
import com.futurecollars.soap.GetAllInvoicesResponse;
import com.futurecollars.soap.GetInvoiceByIdRequest;
import com.futurecollars.soap.GetInvoiceByIdResponse;
import com.futurecollars.soap.InvoiceList;
import com.futurecollars.soap.InvoiceRemoveByIdRequest;
import com.futurecollars.soap.InvoiceRemoveByIdResponse;
import com.futurecollars.soap.InvoiceUpdateRequest;
import com.futurecollars.soap.InvoiceUpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Endpoint
public class InvoiceEndpoint {

  private static final String NAMESPACE_URI = "http://futurecollars.com/soap";

  @Autowired
  private InvoiceBook invoiceBook;

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllInvoicesRequest")
  @ResponsePayload
  public GetAllInvoicesResponse getAllInvoices(
      @RequestPayload GetAllInvoicesRequest request) {
    GetAllInvoicesResponse response = new GetAllInvoicesResponse();
    List<com.futurecollars.soap.Invoice> soapInvoiceList = new ArrayList<>();
    InvoiceList responseInvoiceList = new InvoiceList();
    try {
      List<Invoice> invoiceList = invoiceBook.getInvoices();
      invoiceList.forEach(invoice ->
          soapInvoiceList.add(InvoiceSoapMapper
              .INSTANCE.toInvoiceSoap(invoice)));
      responseInvoiceList.getInvoices().addAll(soapInvoiceList);
      response.setInvoiceList(responseInvoiceList);
    } catch (DatabaseOperationException ex) {
      ex.printStackTrace();
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceByIdRequest")
  @ResponsePayload
  public GetInvoiceByIdResponse getInvoice(
      @RequestPayload GetInvoiceByIdRequest request) {
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

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "invoiceUpdateRequest")
  @ResponsePayload
  public InvoiceUpdateResponse updateInvoice(
      @RequestPayload InvoiceUpdateRequest request) {

    Invoice invoice = InvoiceSoapMapper.INSTANCE.toInvoice(request.getInvoice());
    InvoiceUpdateResponse response = new InvoiceUpdateResponse();

    try {
      Invoice savedInvoice = invoiceBook.saveInvoice(invoice);
      response.setInvoice(InvoiceSoapMapper.INSTANCE.toInvoiceSoap(savedInvoice));
    } catch (DatabaseOperationException ex) {
      ex.printStackTrace();
      response.getErrorMsg().add(ex.getMessage());
    }
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "invoiceRemoveByIdRequest")
  @ResponsePayload
  public InvoiceRemoveByIdResponse removeInvoice(
      @RequestPayload InvoiceRemoveByIdRequest request) {

    UUID uuid = UUID.fromString(request.getId());
    InvoiceRemoveByIdResponse response = new InvoiceRemoveByIdResponse();

    try {
      invoiceBook.removeInvoiceById(uuid);
      response.setResponse("Invoice id: " + uuid + " has been removed.");
    } catch (DatabaseOperationException ex) {
      ex.printStackTrace();
      response.setResponse(ex.getMessage());
    }

    return response;
  }
}
