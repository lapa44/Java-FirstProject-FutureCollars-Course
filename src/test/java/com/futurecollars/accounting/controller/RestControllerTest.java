package com.futurecollars.accounting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurecollars.accounting.domain.model.DataGenerator;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.service.InvoiceBook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class RestControllerTest {

  @Autowired
  private ObjectMapper mapper;

  private String asJsonString(final Invoice invoice) throws JsonProcessingException {
    return mapper.writeValueAsString(invoice);
  }

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private InvoiceBook book;

  @Test
  public void shouldSaveInvoice() throws Exception {
    Invoice invoice = DataGenerator.randomInvoice().build();
    mockMvc.perform(
        MockMvcRequestBuilders.post("/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(invoice)))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldGettingInvoices() throws Exception {
    UUID id = UUID.randomUUID();
    LocalDate today = LocalDate.now();
    when(book.getInvoices())
        .thenReturn(Collections.singletonList(
            DataGenerator.randomInvoice()
              .setId(id)
              .setDate(today)
              .setInvoiceNumber("123")
              .build()));
    this.mockMvc.perform(get("/invoices"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].invoiceNumber").value("123"))
        .andExpect(jsonPath("$[0].date")
            .value(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(today)))
        .andExpect(jsonPath("$[0].id").value(id.toString()))
        .andExpect(jsonPath("$[0].entries").isNotEmpty())
        .andReturn();
  }

  @Test
  public void shouldGettingInvoiceById() throws Exception {
    UUID id = UUID.randomUUID();
    LocalDate today = LocalDate.now();
    when(book.getInvoiceById(id))
        .thenReturn(
            DataGenerator.randomInvoice()
              .setId(id)
              .setDate(today)
              .setInvoiceNumber("123")
              .build());
    this.mockMvc.perform(get("/invoices/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.invoiceNumber").value("123"))
        .andExpect(jsonPath("$.date")
            .value(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(today)))
        .andExpect(jsonPath("$.id").value(id.toString()))
        .andExpect(jsonPath("$.entries").isNotEmpty());
  }

  @Test
  public void shouldRemovingInvoiceById() throws Exception {
    UUID id = UUID.randomUUID();
    this.mockMvc.perform(delete("/invoices/" + id))
        .andExpect(jsonPath("$").doesNotHaveJsonPath());
    verify(book).removeInvoiceById(id);
  }

  @Test
  public void shouldReturnsStatus400_ForNullIdWhileGettingInvoiceById() throws Exception {
    UUID id = null;
    when(book.getInvoiceById(id))
        .thenReturn(
            DataGenerator.randomInvoice()
              .setId(id)
              .build());
    mockMvc.perform(get("/invoices/" + id))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$").doesNotHaveJsonPath());
  }
}
