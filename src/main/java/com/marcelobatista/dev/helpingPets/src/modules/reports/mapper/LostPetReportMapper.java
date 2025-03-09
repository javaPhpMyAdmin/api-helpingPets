package com.marcelobatista.dev.helpingPets.src.modules.reports.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.LostPetReport;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.CreateLostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.LostPetReportDTO;

@Mapper(componentModel = "spring")
public interface LostPetReportMapper {

  @Mapping(target = "reporterId", source = "reporter.id")
  LostPetReportDTO toDto(LostPetReport entity);

  @Mapping(target = "reporter", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "reportType", ignore = true)
  @Mapping(target = "imageUrls", ignore = true)
  LostPetReport toEntity(CreateLostPetReportDTO dto);

  List<LostPetReportDTO> toDtoList(Page<LostPetReport> entities);

}
