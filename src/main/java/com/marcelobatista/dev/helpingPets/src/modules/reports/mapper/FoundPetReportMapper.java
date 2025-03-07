package com.marcelobatista.dev.helpingPets.src.modules.reports.mapper;

import org.mapstruct.Mapper;

import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.FoundPetReport;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.CreateFoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.FoundPetReportDTO;

@Mapper(componentModel = "spring")
public interface FoundPetReportMapper {

  FoundPetReportDTO toDto(FoundPetReport entity);

  FoundPetReport toEntity(CreateFoundPetReportDTO dto);
}
