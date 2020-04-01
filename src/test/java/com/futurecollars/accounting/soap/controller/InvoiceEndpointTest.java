package com.futurecollars.accounting.soap.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;

import com.futurecollars.TestConfig;
import com.futurecollars.accounting.configuration.WebServiceConfig;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import com.futurecollars.accounting.service.InvoiceBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import javax.xml.transform.Source;

@SpringBootTest
@ContextConfiguration(classes = {WebServiceConfig.class, TestConfig.class})
class InvoiceEndpointTest {

  private static final int INVOICES_NUMBER = 4;
  private static final String RESOURCES_PATH = "src/test/resources/soapRequestsXml/";
  @Autowired
  private InvoiceBook invoiceBook;
  @Autowired
  private InvoiceEndpoint invoiceEndpoint;
  @Autowired
  private WebApplicationContext applicationContext;
  private MockWebServiceClient mockClient;
  private Resource xsdSchema = new ClassPathResource("xsd/invoices.xsd");

  @BeforeEach
  public void createMockClient() throws DatabaseOperationException {
    mockClient = MockWebServiceClient.createClient(applicationContext);
    List<Invoice> invoiceList = invoiceBook.getInvoices();
    if (invoiceList.size() > 0) {
      for (Invoice invoice : invoiceList) {
        UUID id = invoice.getId();
        invoiceBook.removeInvoiceById(id);
      }
    }
  }

  @RepeatedTest(3)
  public void shouldGetAllInvoices() throws IOException {

    Source requestPayload = getRequest("saveInvoiceRequest.xml");
    Source requestAllPayload = getRequest("getAllInvoicesRequest.xml");

    for (int i = 0; i < INVOICES_NUMBER; i++) {
      mockClient
          .sendRequest(withPayload(requestPayload))
          .andExpect(noFault());
    }

    mockClient
        .sendRequest(withPayload(requestAllPayload))
        .andExpect(noFault())
        .andExpect(validPayload(xsdSchema));

    int countInvoices =
        invoiceEndpoint
            .getAllInvoices(null)
            .getInvoiceList()
            .getInvoices()
            .size();

    assertThat(countInvoices).isEqualTo(INVOICES_NUMBER);
  }

  @RepeatedTest(3)
  public void shouldSaveInvoice() throws IOException {

    Source requestPayload = getRequest("saveInvoiceRequest.xml");

    mockClient
        .sendRequest(withPayload(requestPayload))
        .andExpect(noFault())
        .andExpect(validPayload(xsdSchema));
  }

  @RepeatedTest(3)
  public void shouldRemoveInvoiceById()
      throws IOException, DatabaseOperationException {
    Source requestPayload = getRequest("saveInvoiceRequest.xml");

    mockClient
        .sendRequest(withPayload(requestPayload))
        .andExpect(noFault());

    UUID id = invoiceBook.getInvoices().get(0).getId();

    requestPayload = new StringSource(
        "<gs:invoiceRemoveByIdRequest xmlns:gs=\"http://futurecollars.com/soap\">"
            + "<gs:id>" + id + "</gs:id>"
            + "</gs:invoiceRemoveByIdRequest>"
    );

    Source responsePayload = new StringSource(
        "<ns2:invoiceRemoveByIdResponse xmlns:ns2=\"http://futurecollars.com/soap\">"
            + "<ns2:response>Invoice id: " + id + " has been removed.</ns2:response>"
            + "</ns2:invoiceRemoveByIdResponse>"
    );

    mockClient
        .sendRequest(withPayload(requestPayload))
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @RepeatedTest(3)
  public void shouldGetInvoiceById()
      throws IOException, DatabaseOperationException {
    Source requestPayload = getRequest("saveInvoiceRequest.xml");

    mockClient
        .sendRequest(withPayload(requestPayload))
        .andExpect(noFault());

    UUID id = invoiceBook.getInvoices().get(0).getId();

    requestPayload = new StringSource(
        "<gs:getInvoiceByIdRequest xmlns:gs=\"http://futurecollars.com/soap\">"
            + "<gs:id>" + id + "</gs:id>"
            + "</gs:getInvoiceByIdRequest>"
    );

    mockClient
        .sendRequest(withPayload(requestPayload))
        .andExpect(noFault())
        .andExpect(validPayload(xsdSchema));
  }

  private Source getRequest(String file) throws IOException {
    String request = Files.readString(Paths.get(RESOURCES_PATH + file));
    return new StringSource(request);
  }
}