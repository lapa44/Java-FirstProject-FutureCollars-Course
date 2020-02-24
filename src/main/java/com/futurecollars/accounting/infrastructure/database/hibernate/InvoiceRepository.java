package com.futurecollars.accounting.infrastructure.database.hibernate;

import java.util.UUID;
import com.futurecollars.accounting.infrastructure.database.hibernate.model.InvoiceHibernate;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<InvoiceHibernate, UUID> {
}
