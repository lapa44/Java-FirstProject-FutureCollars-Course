package com.futurecollars.accounting.soap.mapper;

import com.futurecollars.accounting.domain.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(imports = UUID.class)
public interface CompanySoapMapper {

  CompanySoapMapper INSTANCE = Mappers.getMapper(CompanySoapMapper.class);

  @Mappings({
      @Mapping(target = "taxIdentificationNumber", expression = "java(UUID.randomUUID())")})
  Company toCompany(com.futurecollars.soap.Company companySoap);

  @Mappings({
      @Mapping(target = "taxIdentificationNumber", expression = "java(String.valueOf(company"
          + ".getTaxIdentificationNumber()))")})
  com.futurecollars.soap.Company toCompanySoap(Company company);

}
