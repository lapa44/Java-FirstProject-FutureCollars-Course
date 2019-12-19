package com.futurecollars.invoices.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Invoice {

  private UUID id;
  private LocalDate date;
  private Company seller;
  private Company buyer;
  private List<InvoiceEntry> entries;

}
