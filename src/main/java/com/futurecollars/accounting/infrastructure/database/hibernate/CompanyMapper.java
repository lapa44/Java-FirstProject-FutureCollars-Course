package com.futurecollars.accounting.infrastructure.database.hibernate;

import com.futurecollars.accounting.domain.model.Company;
import com.futurecollars.accounting.infrastructure.database.hibernate.model.CompanyHibernate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyMapper {

  CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

  Company toCompany(CompanyHibernate companyHibernate);

  CompanyHibernate toCompanyHibernate(Company company);
}
