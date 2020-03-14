package com.futurecollars.accounting.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.futurecollars.accounting.domain.model.InvoiceEntry;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceCalculateTotalValue {

  public BigDecimal getTotalValue(List<InvoiceEntry> entries) {
    return entries.stream()
        .map(InvoiceEntry::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }


  public BigDecimal getTotalValueWithTaxes(List<InvoiceEntry> entries) {
    return entries.stream()
        .map(InvoiceEntry::getPriceWithTax)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
