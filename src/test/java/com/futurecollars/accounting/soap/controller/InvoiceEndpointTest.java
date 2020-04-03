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
import java.util.List;
import java.util.UUID;
import javax.xml.transform.Source;

@SpringBootTest
@ContextConfiguration(classes = {WebServiceConfig.class, TestConfig.class})
class InvoiceEndpointTest {

  @Autowired
  private InvoiceBook invoiceBook;
  @Autowired
  private InvoiceEndpoint invoiceEndpoint;
  @Autowired
  private WebApplicationContext applicationContext;
  private MockWebServiceClient mockClient;
  private Resource xsdSchema = new ClassPathResource("xsd/invoices.xsd");
  private static final int INVOICES_NUMBER = 3;

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
    Source requestAllPayload = new StringSource(getAllInvoicesRequest());

    for (int i = 0; i < INVOICES_NUMBER; i++) {
      Source requestPayload = new StringSource(saveInvoiceRequest());
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
    Source requestPayload = new StringSource(saveInvoiceRequest());

    mockClient
        .sendRequest(withPayload(requestPayload))
        .andExpect(noFault())
        .andExpect(validPayload(xsdSchema));
  }

  @RepeatedTest(3)
  public void shouldRemoveInvoiceById()
      throws IOException, DatabaseOperationException {
    Source requestPayload = new StringSource(saveInvoiceRequest());

    mockClient
        .sendRequest(withPayload(requestPayload))
        .andExpect(noFault());

    UUID id = invoiceBook.getInvoices().get(0).getId();
    requestPayload = new StringSource(removeInvoiceByIdRequest(id));
    Source responsePayload = new StringSource(removeInvoiceByIdResponse(id));

    mockClient
        .sendRequest(withPayload(requestPayload))
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @RepeatedTest(3)
  public void shouldGetInvoiceById()
      throws IOException, DatabaseOperationException {
    Source requestPayload = new StringSource(saveInvoiceRequest());

    mockClient
        .sendRequest(withPayload(requestPayload))
        .andExpect(noFault());

    UUID id = invoiceBook.getInvoices().get(0).getId();
    requestPayload = new StringSource(getInvoiceByIdRequest(id));

    mockClient
        .sendRequest(withPayload(requestPayload))
        .andExpect(noFault())
        .andExpect(validPayload(xsdSchema));
  }

  private String getInvoiceByIdRequest(UUID id) {
    String request = "<gs:getInvoiceByIdRequest xmlns:gs=\"http://futurecollars.com/soap\">"
        + "<gs:id>" + id + "</gs:id>"
        + "</gs:getInvoiceByIdRequest>";
    return request;
  }

  private String removeInvoiceByIdResponse(UUID id) {
    String response = "<ns2:invoiceRemoveByIdResponse xmlns:ns2=\"http://futurecollars.com/soap\">"
        + "<ns2:response>Invoice id: " + id + " has been removed.</ns2:response>"
        + "</ns2:invoiceRemoveByIdResponse>";
    return response;
  }

  private String removeInvoiceByIdRequest(UUID id) {
    String request = "<gs:invoiceRemoveByIdRequest xmlns:gs=\"http://futurecollars.com/soap\">"
        + "<gs:id>" + id + "</gs:id>"
        + "</gs:invoiceRemoveByIdRequest>";
    return request;
  }

  private String getAllInvoicesRequest() {
    String request =
        "<gs:getAllInvoicesRequest xmlns:gs=\"http://futurecollars.com/soap\">"
            + "</gs:getAllInvoicesRequest>";
    return request;
  }

  private String saveInvoiceRequest() {
    int invoiceNumber1 = (int) (Math.random() * 9999.999);
    int invoiceNumber2 = (int) (Math.random() * 9999.999);
    String request = "<gs:invoiceSaveRequest xmlns:gs=\"http://futurecollars.com/soap\">"
        + "<gs:invoice>"
        + "<gs:invoiceNumber>" + invoiceNumber1 + "-" + invoiceNumber2 + "</gs:invoiceNumber>"
        + "<gs:date>2020-02-22</gs:date>"
        + "<gs:buyer>"
        + "<gs:taxIdentificationNumber>6cfbb51b-3b5e-4e51-8338-a9e4328be456"
        + "</gs:taxIdentificationNumber>"
        + "<gs:address>Apt. 224 39592 Jon Meadow, East Gail, WA 40986-7258</gs:address>"
        + "<gs:name>Kimber Kulas</gs:name>"
        + "</gs:buyer>"
        + "<gs:seller>"
        + "<gs:taxIdentificationNumber>fd78adf0-710d-4a63-9e61-3978b1783605"
        + "</gs:taxIdentificationNumber>"
        + "<gs:address>7137 Hirthe Stream, East Bethville, NH 95976</gs:address>"
        + "<gs:name>Kiyoko Herman III</gs:name>"
        + "</gs:seller>"
        + "<gs:entries>"
        + "<gs:description>Chair</gs:description>"
        + "<gs:unit>pcs</gs:unit>"
        + "<gs:quantity>10</gs:quantity>"
        + "<gs:unitPrice>105</gs:unitPrice>"
        + "<gs:vatRate>VAT_5</gs:vatRate>"
        + "</gs:entries>"
        + "<gs:entries>"
        + "<gs:description>Small Copper Plate</gs:description>"
        + "<gs:unit>hrs</gs:unit>"
        + "<gs:quantity>5</gs:quantity>"
        + "<gs:unitPrice>20.1</gs:unitPrice>"
        + "<gs:vatRate>VAT_8</gs:vatRate>"
        + "</gs:entries>"
        + "</gs:invoice>"
        + "</gs:invoiceSaveRequest>";
    return request;
  }
}