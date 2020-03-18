package com.futurecollars.accounting.soap.mapper;

import com.futurecollars.accounting.domain.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanySoapMapper {

  CompanySoapMapper INSTANCE = Mappers.getMapper(CompanySoapMapper.class);

  Company toCompany(com.futurecollars.soap.Company companySoap);

  com.futurecollars.soap.Company toCompanySoap(Company company);

}
