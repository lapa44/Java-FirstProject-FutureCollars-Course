package com.futurecollars.accounting.configuration;

import com.futurecollars.accounting.service.InvoiceBook;
import com.futurecollars.accounting.service.MailService;
import com.futurecollars.accounting.service.MailServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailConfig {

  @Autowired
  InvoiceBook invoiceBook;

  @Bean
  public MailServiceAdapter mailServiceAdapter(MailService mailService) {
    MailServiceAdapter mailServiceAdapter = new MailServiceAdapter(mailService);
    invoiceBook.registerObserver(mailServiceAdapter);
    return mailServiceAdapter;
  }

  @Bean
  public MailService mailService(JavaMailSender javaMailSender) {
    return new MailService(javaMailSender);
  }
}
