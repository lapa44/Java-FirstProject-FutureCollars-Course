package com.futurecollars.accounting.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.futurecollars.accounting.domain.model.Vat;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

class InvoiceCalculateTotalValueTest {

  @Test
  public void shouldReturnTotalValue() {
    //given
    InvoiceCalculateTotalValue invoiceCalculateTotalValue = new InvoiceCalculateTotalValue();
    Invoice invoice = new Invoice(UUID.randomUUID(), "No1", LocalDate.now(),
        new Company(UUID.randomUUID(), " ", " "), new Company(UUID.randomUUID(),
        " ", " "), Arrays.asList(new InvoiceEntry("Tequila", 1,
            new BigDecimal("20.00"), Vat.VAT_23),
        new InvoiceEntry("Cola", 1, new BigDecimal("5"), Vat.VAT_8)));

    //when
    BigDecimal actual = invoiceCalculateTotalValue.getTotalValue(invoice.getEntries());

    //then
    assertEquals(new BigDecimal("25.00"), actual);
  }

  @Test
  public void shouldReturnTotalValueWithTaxes() {
    //given
    InvoiceCalculateTotalValue invoiceCalculateTotalValue = new InvoiceCalculateTotalValue();
    Invoice invoice = new Invoice(UUID.randomUUID(), "No1", LocalDate.now(),
        new Company(UUID.randomUUID(), " ", " "), new Company(UUID.randomUUID(),
        " ", " "), Arrays.asList(new InvoiceEntry("Tequila", 1,
            new BigDecimal("20"), Vat.VAT_23), new InvoiceEntry("Cola", 1,
        new BigDecimal("5"), Vat.VAT_8)));

    //when
    BigDecimal actual = invoiceCalculateTotalValue.getTotalValueWithTaxes(invoice.getEntries());

    //then
    assertEquals(new BigDecimal("30.00"), actual);
  }
}
