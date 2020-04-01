package com.futurecollars.accounting.infrastructure.database.postgres;

import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.domain.model.Invoice;
import com.futurecollars.accounting.domain.model.InvoiceEntry;
import com.futurecollars.accounting.domain.model.Vat;
import com.futurecollars.accounting.infrastructure.database.Database;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class SQLWithDriverDatabase implements Database {

  private final JdbcTemplate jdbcTemplate;

  public SQLWithDriverDatabase(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static List<Invoice> extractData(ResultSet rs) throws SQLException {
    Map<UUID, Invoice> invoices = new HashMap<>();
    while (rs.next()) {
      Date date = rs.getDate("date");
      Invoice.builder()
          .setId((UUID) rs.getObject("id"))
          .setInvoiceNumber(rs.getString("\"invoice_number\""))
          .setDate(date.toLocalDate())
          .build();

      InvoiceEntry.builder()
          .setDescription(rs.getString("description"))
          .setUnit(rs.getString("unit"))
          .setQuantity(rs.getInt("quantity"))
          .setUnitPrice(rs.getBigDecimal("unit_price"))
//          .setPrice(rs.getBigDecimal("price"))
          .setVatRate((Vat) rs.getObject("vat_rate"))
//          .setVatValue(rs.getBigDecimal("vat_value"))
          .build();

      Company.builder()
          .setTaxIdentificationNumber((UUID) rs.getObject("tax_identification_number"))
          .setAddress(rs.getString("address"))
          .setName(rs.getString("name"))
          .build();
    }
    return (List<Invoice>) invoices.values();
  }

  @Override
  public Invoice saveInvoice(Invoice invoice) {
    String sql = "INSERT INTO invoices (date, invoice_number) VALUES (?, ?)";
    return jdbcTemplate.update(sql, new Object[]{(rs, i) ->
        new Invoice()}
  }


//  @Override
//  public Invoice getInvoiceById(UUID id) {
//    String sql = "SELECT * FROM invoices WHERE id = ?";
//    return jdbcTemplate.queryForObject(sql, new Object[]{id},
//        (rs, i) ->
//            Invoice.builder()
//                .setId((UUID) rs.getObject("id"))
//                .setInvoiceNumber(rs.getString("\"invoice_number\""))
//                .setDate(rs.getDate("date").toLocalDate())
////                .build();
//    );
//  }

  @Override
  public Invoice getInvoiceById(UUID id) {
    String sql = "SELECT * FROM invoices WHERE id = ?";
    return jdbcTemplate.queryForObject("SELECT * "
            + "FROM invoices i"
            + "LEFT OUTER JOIN entries e"
            + "ON i.invoice_id = e.invoice_id"
            + "LEFT OUTER JOIN buyers b"
            + "ON i.invoice_id = b.invoice_id"
            + "LEFT OUTER JOIN sellers s"
            + "ON i.invoice_id = s.invoice_id",
        SQLWithDriverDatabase::extractData);
  }

  @Override
  public List<Invoice> getInvoices() {
    return jdbcTemplate.query("SELECT * "
            + "FROM invoices i"
            + "LEFT JOIN entries e"
            + "ON i.invoice_id = e.invoice_id"
            + "LEFT JOIN buyers b"
            + "ON i.invoice_id = b.invoice_id"
            + "LEFT JOIN sellers s"
            + "ON i.invoice_id = s.invoice_id",
        SQLWithDriverDatabase::extractData);
  }

  @Override
  public Invoice removeInvoiceById(UUID id) {
    Invoice invoice = getInvoiceById(id);
    if (invoice == null) {
      return null;
    }
    jdbcTemplate.update("DELETE FROM invoices WHERE id = ?", id);
    return invoice;
  }
}
