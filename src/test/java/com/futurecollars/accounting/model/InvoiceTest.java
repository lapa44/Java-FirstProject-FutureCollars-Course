package com.futurecollars.accounting.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvoiceTest {

    @Test
    public void shouldReturnTotalValue() {
        //given
        Invoice invoice = new Invoice(UUID.randomUUID(), new Date(), new Company(), new Company(),
                Arrays.asList(new InvoiceEntry("Tequila", new BigDecimal("20.00"), VAT.VAT_23),
                        new InvoiceEntry("Cola", new BigDecimal("5"), VAT.VAT_8)));

        //when
        BigDecimal actual = invoice.getTotalValue();

        //then
        assertEquals(new BigDecimal("25.00"), actual);
    }

    @Test
    public void shouldReturnTotalValueWithTaxes() {
        //given
        Invoice invoice = new Invoice(UUID.randomUUID(), new Date(), new Company(), new Company(),
                Arrays.asList(new InvoiceEntry("Tequila", new BigDecimal("20"), VAT.VAT_23),
                        new InvoiceEntry("Cola", new BigDecimal("5"), VAT.VAT_8)));

        //when
        BigDecimal actual = invoice.getTotalValueWithTaxes();

        //then
        assertEquals(new BigDecimal("30.00"), actual);
    }
}
