package com.futurecollars.accounting.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import com.github.javafaker.Faker;

public class DataGenerator {
  private static Faker faker = new Faker();

  public static Company.Builder randomCompany() {
    return Company.builder()
        .setTaxIdentificationNumber( UUID.randomUUID())
        .setAddress(faker.address().fullAddress())
        .setName(faker.name().name());
  }

  public static InvoiceEntry.Builder randomEntry() {
    return InvoiceEntry.builder()
        .setDescription(faker.commerce().productName())
        .setUnit(faker.random().nextInt(1, 100))
        .setPrice(new BigDecimal(faker.number().randomNumber(3, false)))
        .setVatRate(Vat.values()[faker.random().nextInt(Vat.values().length)]);
  }

  public static List<InvoiceEntry> randomEntries(int numberOfEntries) {
    List<InvoiceEntry> entries = new ArrayList<>();
    for (int i = 0; i < numberOfEntries; i++) {
      entries.add(randomEntry().build());
    }
    return entries;
  }

  public static Invoice.Builder randomInvoice() {
    return Invoice.builder()
        .setInvoiceNumber(faker.idNumber().valid())
        .setDate(LocalDate.ofInstant(
            faker.date().past(
                faker.random().nextInt(1,10), TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
        .setBuyer(randomCompany().build())
        .setSeller(randomCompany().build())
        .setEntries(randomEntries(faker.number().randomDigitNotZero()));
  }
}
