package com.futurecollars.accounting.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import com.futurecollars.accounting.domain.model.DataGenerator;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.Vat;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

class InvoiceCalculateTotalValueTest {

  @Test
  public void shouldReturnTotalValue() {
    //given
    InvoiceCalculateTotalValue invoiceCalculateTotalValue = new InvoiceCalculateTotalValue();
    Invoice invoice = DataGenerator.randomInvoice()
        .clearEntries()
        .setEntries(Arrays.asList(
            DataGenerator.randomEntry()
              .setQuantity(1)
              .setUnitPrice(new BigDecimal("5"))
              .setVatRate(Vat.VAT_8)
              .build(),
            DataGenerator.randomEntry()
                .setQuantity(1)
                .setUnitPrice(new BigDecimal("20"))
                .setVatRate(Vat.VAT_23)
                .build()
        )).build();

    //when
    BigDecimal actual = invoiceCalculateTotalValue.getTotalValue(invoice.getEntries());

    //then
    assertThat(actual.compareTo(new BigDecimal("25.00"))).isEqualTo(0);
  }

  @Test
  public void shouldReturnTotalValueWithTaxes() {
    //given
    InvoiceCalculateTotalValue invoiceCalculateTotalValue = new InvoiceCalculateTotalValue();
    Invoice invoice = DataGenerator.randomInvoice()
        .clearEntries()
        .setEntries(Arrays.asList(
            DataGenerator.randomEntry()
                .setQuantity(1)
                .setUnitPrice(new BigDecimal("5"))
                .setVatRate(Vat.VAT_8)
                .build(),
            DataGenerator.randomEntry()
                .setQuantity(1)
                .setUnitPrice(new BigDecimal("20"))
                .setVatRate(Vat.VAT_23)
                .build()
        )).build();
    //when
    BigDecimal actual = invoiceCalculateTotalValue.getTotalValueWithTaxes(invoice.getEntries());

    //then
    assertThat(actual.compareTo(new BigDecimal("30.00"))).isEqualTo(0);
  }
}
