package com.marcelobatista.dev.helpingPets.src.modules.reports.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.FoundPetReport;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTO;

@Mapper(componentModel = "spring")
public interface FoundPetReportMapper {
  // @Mapping(target = "reportedAt", source = "createdAt")
  FoundPetReportDTO toDto(FoundPetReport entity);

  FoundPetReport toEntity(FoundPetReportDTO dto);
}
