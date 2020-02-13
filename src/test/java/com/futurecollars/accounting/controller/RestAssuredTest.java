package com.futurecollars.accounting.controller;

import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.futurecollars.accounting.domain.model.Vat;
import com.futurecollars.accounting.service.InvoiceBook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class RestAssuredTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceBook book;

    @Test
    public void shouldReturnDefaultMessageForGettingInvoiceById() throws Exception {
        UUID id = UUID.randomUUID();
        UUID taxId2 = UUID.randomUUID();
        UUID taxId3 = UUID.randomUUID();
        LocalDate today = LocalDate.now();
        Mockito.when(book.getInvoiceById(id))
                .thenReturn(
                        new Invoice(id, "123", today,
                                new Company(taxId2, "address2", "company2"),
                                new Company(taxId3, "address3", "company3"),
                                Collections.singletonList(new InvoiceEntry("computer", "unit",
                                        new BigDecimal("2.25"), Vat.VAT_23))));
        given()
                .mockMvc(mockMvc)
                .when()
                .get("/invoices/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.toString()))
                .body("date", equalTo(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(today)))
                .body("entries", notNullValue())
                .body("invoiceNumber", equalTo("123"));
    }
}
