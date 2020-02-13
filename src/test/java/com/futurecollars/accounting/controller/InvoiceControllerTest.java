package com.futurecollars.accounting.controller;

import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.futurecollars.accounting.domain.model.Vat;
import com.futurecollars.accounting.infrastructure.database.DatabaseOperationException;
import com.futurecollars.accounting.service.InvoiceBook;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class InvoiceControllerTest {

    @InjectMocks
    private InvoiceController controller;

    @Mock
    private InvoiceBook book;

    @Test
    public void shouldSaveInvoice() throws DatabaseOperationException {
        //given
        InvoiceBook book = mock(InvoiceBook.class);
        InvoiceController controller = new InvoiceController(book);
        Invoice invoice = new Invoice(UUID.randomUUID(), "123", LocalDate.now(),
                new Company(UUID.randomUUID(), "address2", "company2"),
                new Company(UUID.randomUUID(), "address3", "company3"),
                Collections.singletonList(new InvoiceEntry("computer", "unit",
                        new BigDecimal("2.25"), Vat.VAT_23)));
        //when
        controller.saveInvoice(invoice);
        //then
        verify(book).saveInvoice(invoice);
        assertNotNull(controller.saveInvoice(invoice));
    }

    @Test
    public void shouldReturnInvoiceById() throws DatabaseOperationException {
        //given
        InvoiceBook book = mock(InvoiceBook.class);
        InvoiceController controller = new InvoiceController(book);
        UUID id = UUID.randomUUID();
        Invoice invoice = new Invoice(UUID.randomUUID(), "123", LocalDate.now(),
                new Company(UUID.randomUUID(), "address2", "company2"),
                new Company(UUID.randomUUID(), "address3", "company3"),
                Collections.singletonList(new InvoiceEntry("computer", "unit",
                        new BigDecimal("2.25"), Vat.VAT_23)));
        //when
        controller.getInvoiceById(id);
        //then
        verify(book).getInvoiceById(id);
        assertNotNull(controller.saveInvoice(invoice));
        assertThat(controller.getInvoiceById(id).equals(invoice));
    }

    @Test
    public void shouldReturnInvoices() {
        //given
        InvoiceBook book = mock(InvoiceBook.class);
        InvoiceController controller = new InvoiceController(book);
        UUID id = UUID.randomUUID();
        when(book.getInvoices()).thenReturn(Arrays.asList(
                new Invoice(id, "123", LocalDate.now(),
                        new Company(UUID.randomUUID(), "address2", "company2"),
                        new Company(UUID.randomUUID(), "address3", "company3"),
                        Collections.singletonList(new InvoiceEntry("computer", "unit",
                                new BigDecimal("2.25"), Vat.VAT_23)))));
        //when
        List<Invoice> result = controller.getInvoices();
        //then
        verify(book).getInvoices();
        assertThat(result).hasSize(1);
        assertThat(result.equals(Arrays.asList()));
        assertThat(result.iterator().next().getId()).isEqualTo(id);
        assertEquals(1, book.getInvoices().size());
    }

    @Test
    public void shouldRemoveInvoiceById() throws DatabaseOperationException {
        //given
        InvoiceBook book = mock(InvoiceBook.class);
        InvoiceController controller = new InvoiceController(book);
        UUID id = UUID.randomUUID();
        Invoice invoice = new Invoice(id, "123", LocalDate.now(),
                new Company(UUID.randomUUID(), "address2", "company2"),
                new Company(UUID.randomUUID(), "address3", "company3"),
                Collections.singletonList(new InvoiceEntry("computer", "unit",
                        new BigDecimal("2.25"), Vat.VAT_23)));

        //when
        controller.removeInvoiceById(id);
        //then
        verify(book).removeInvoiceById(id);
        assertEquals(null, book.removeInvoiceById(id));
    }
}
