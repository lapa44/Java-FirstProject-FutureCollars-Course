package com.futurecollars.accounting.database;

import com.futurecollars.accounting.model.Company;
import com.futurecollars.accounting.model.Invoice;
import com.futurecollars.accounting.model.InvoiceEntry;
import com.futurecollars.accounting.model.VAT;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryDatabaseTest {

    @Test
    void shouldSaveInvoiceToMemoryAndReturnItById() {
        //given
        InMemoryDatabase database = new InMemoryDatabase();
        UUID id = UUID.randomUUID();
        Invoice invoice = new Invoice(id, new Date(), new Company(), new Company(),
                Arrays.asList(new InvoiceEntry("Tequila", new BigDecimal("20"), VAT.VAT_23),
                        new InvoiceEntry("Cola", new BigDecimal("5"), VAT.VAT_8)));

        //when
        database.saveInvoice(invoice);

        //then
        assertEquals(invoice, database.getInvoiceById(id));
    }

    @Test
    void shouldReturnAllInvoices() {
        //given
        InMemoryDatabase database = new InMemoryDatabase();
        Invoice invoice = new Invoice(UUID.randomUUID(), new Date(), new Company(), new Company(),
                Arrays.asList(new InvoiceEntry("Tequila", new BigDecimal("20"), VAT.VAT_23),
                        new InvoiceEntry("Cola", new BigDecimal("5"), VAT.VAT_8)));
        Invoice invoice2 = new Invoice(UUID.randomUUID(), new Date(), new Company(), new Company(),
                Arrays.asList(new InvoiceEntry("Sprite", new BigDecimal("3.33"), VAT.VAT_23),
                        new InvoiceEntry("Tea", new BigDecimal("2.42"), VAT.VAT_8)));

        //when
        database.saveInvoice(invoice);
        database.saveInvoice(invoice2);

        //then
        assertEquals(Arrays.asList(invoice, invoice2), database.getInvoices());
    }

    @Test
    void shouldUpdateInvoice() {
        //given
        InMemoryDatabase database = new InMemoryDatabase();
        UUID id = UUID.randomUUID();
        Invoice invoice = new Invoice(id, new Date(), new Company(), new Company(),
                Arrays.asList(new InvoiceEntry("Tequila", new BigDecimal("20"), VAT.VAT_23),
                        new InvoiceEntry("Cola", new BigDecimal("5"), VAT.VAT_8)));

        //when
        database.saveInvoice(invoice);
        invoice.addEntry(new InvoiceEntry("Pepsi", new BigDecimal("3"), VAT.VAT_5));
        database.updateInvoice(invoice);

        //then
        assertEquals(invoice, database.getInvoiceById(id));
    }

    @Test
    void shouldThrowExceptionForNoInvoiceWhileGettingById() {
        InMemoryDatabase database = new InMemoryDatabase();
        assertThrows(NoSuchElementException.class, () -> database.getInvoiceById(UUID.randomUUID()));
    }

    @Test
    void shouldThrowExceptionForNoInvoicesWhileUpdating() {
        InMemoryDatabase database = new InMemoryDatabase();
        assertThrows(NoSuchElementException.class, () -> database.updateInvoice(new Invoice(UUID.randomUUID(), new Date(), new Company(), new Company(), new ArrayList<>())));
    }
}
