package com.marcelobatista.dev.helpingPets.src.modules.reports.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.LostPetReport;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTO;

@Mapper(componentModel = "spring")
public interface LostPetReportMapper {
  @Mapping(target = "reportedAt", source = "createdAt")
  LostPetReportDTO toDto(LostPetReport entity);

  @Mapping(target = "reporter", ignore = true)
  LostPetReport toEntity(LostPetReportDTO dto);

  List<LostPetReportDTO> toDtoList(List<LostPetReport> entities);
}
