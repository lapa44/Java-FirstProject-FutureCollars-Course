package com.futurecollars.accounting.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

class InvoiceTest {

  @Test
  public void shouldReturnTotalValue() {
    //given
    Invoice invoice = new Invoice(1, UUID.randomUUID(), LocalDate.now(), new Company(),
        new Company(), Arrays.asList(
        new InvoiceEntry("Tequila", new BigDecimal("20.00"), VAT.VAT_23),
        new InvoiceEntry("Cola", new BigDecimal("5"), VAT.VAT_8)));

    //when
    BigDecimal actual = invoice.getTotalValue();

    //then
    assertEquals(new BigDecimal("25.00"), actual);
  }

  @Test
  public void shouldReturnTotalValueWithTaxes() {
    //given
    Invoice invoice = new Invoice(1, UUID.randomUUID(), LocalDate.now(), new Company(),
        new Company(), Arrays.asList(
        new InvoiceEntry("Tequila", new BigDecimal("20"), VAT.VAT_23),
        new InvoiceEntry("Cola", new BigDecimal("5"), VAT.VAT_8)));

    //when
    BigDecimal actual = invoice.getTotalValueWithTaxes();

    //then
    assertEquals(new BigDecimal("30.00"), actual);
  }
}
