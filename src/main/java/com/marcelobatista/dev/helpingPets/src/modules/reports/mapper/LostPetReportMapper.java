package com.marcelobatista.dev.helpingPets.src.modules.reports.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.LostPetReport;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTO;

@Mapper(componentModel = "spring")
public interface LostPetReportMapper {
  @Mapping(target = "reportedAt", source = "createdAt")
  LostPetReportDTO toDto(LostPetReport entity);

  LostPetReport toEntity(LostPetReportDTO dto);

}
