package com.futurecollars.accounting.controller;

import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.futurecollars.accounting.domain.model.Vat;
import com.futurecollars.accounting.service.InvoiceBook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class WebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceBook book;

    @Test
    public void shouldReturnDefaultMessageForSavingInvoice(Invoice invoice) throws Exception {
        InvoiceBook book = mock(InvoiceBook.class);
        LocalDate today = LocalDate.now();
        UUID id = UUID.randomUUID();
        when(book.saveInvoice(invoice)).
                thenReturn(
                        new Invoice(id, "123", today,
                                new Company(UUID.randomUUID(), "address2", "company2"),
                                new Company(UUID.randomUUID(), "address3", "company3"),
                                Collections.singletonList(new InvoiceEntry("computer", "unit",
                                        new BigDecimal("2.25"), Vat.VAT_23))));

        this.mockMvc.perform(post("/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].invoiceNumber").value("123"))
                .andExpect(jsonPath("$[0].date").value(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(today)))
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].entries").isNotEmpty());
    }

    @Test
    public void shouldReturnDefaultMessageForGettingInvoices() throws Exception {
        UUID id = UUID.randomUUID();
        LocalDate today = LocalDate.now();
        when(book.getInvoices()).
                thenReturn(Collections.singletonList(
                        new Invoice(id, "123", today,
                                new Company(UUID.randomUUID(), "address2", "company2"),
                                new Company(UUID.randomUUID(), "address3", "company3"),
                                Collections.singletonList(new InvoiceEntry("computer", "unit",
                                        new BigDecimal("2.25"), Vat.VAT_23)))));

        this.mockMvc.perform(get("/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].invoiceNumber").value("123"))
                .andExpect(jsonPath("$[0].date").value(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(today)))
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].entries").isNotEmpty());
    }

    @Test
    public void shouldReturnDefaultMessageForRemovingInvoiceById() throws Exception {
        UUID id = UUID.randomUUID();
        this.mockMvc.perform(delete("/invoices/" + id))
                .andExpect(jsonPath("$[0]").doesNotHaveJsonPath());
        verify(book).removeInvoiceById(id);
    }
}
