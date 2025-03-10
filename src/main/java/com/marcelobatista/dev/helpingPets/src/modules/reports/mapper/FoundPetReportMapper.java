package com.marcelobatista.dev.helpingPets.src.modules.reports.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.FoundPetReport;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.CreateFoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.FoundPetReportDTO;

@Mapper(componentModel = "spring")
public interface FoundPetReportMapper {

  @Mapping(target = "reporterId", source = "reporter.id")
  @Mapping(target = "reportedAt", source = "createdAt")
  FoundPetReportDTO toDto(FoundPetReport entity);

  @Mapping(target = "status", source = "foundPetStatus")
  @Mapping(target = "reporter.id", ignore = true)
  @Mapping(target = "reportType", ignore = true)
  @Mapping(target = "imageUrl", ignore = true)
  FoundPetReport toEntity(CreateFoundPetReportDTO dto);

  List<FoundPetReportDTO> toDtoList(List<FoundPetReport> entities);
}
