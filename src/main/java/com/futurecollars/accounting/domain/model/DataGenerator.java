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
  private static Faker FAKER = new Faker();
  private static String[] UNITS = {"Hours, Kilograms, Milliliters, Pieces"};

  public static Company.Builder randomCompany() {
    return Company.builder()
        .setTaxIdentificationNumber( UUID.randomUUID())
        .setAddress(FAKER.address().fullAddress())
        .setName(FAKER.name().name());
  }

  public static InvoiceEntry.Builder randomEntry() {
    return InvoiceEntry.builder()
        .setDescription(FAKER.commerce().productName())
        .setUnit(FAKER.options().nextElement(UNITS))
        .setQuantity(FAKER.random().nextInt(1, 100))
        .setUnitPrice(new BigDecimal(FAKER.number().randomNumber(3, false)))
        .setVatRate(Vat.values()[FAKER.random().nextInt(Vat.values().length)]);
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
        .setInvoiceNumber(FAKER.idNumber().valid())
        .setDate(LocalDate.ofInstant(
            FAKER.date().past(
                FAKER.random().nextInt(1, 10), TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
        .setBuyer(randomCompany().build())
        .setSeller(randomCompany().build())
        .setEntries(randomEntries(FAKER.number().randomDigitNotZero()));
  }
}
