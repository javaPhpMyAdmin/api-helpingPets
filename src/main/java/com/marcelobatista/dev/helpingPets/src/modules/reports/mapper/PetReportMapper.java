package com.marcelobatista.dev.helpingPets.src.modules.reports.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.PetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.FoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.LostPetReportDTO;

@Mapper(componentModel = "spring")
public interface PetReportMapper {
  @Mapping(target = "title", ignore = true)
  @Mapping(source = "reporterId", target = "reportedBy")
  @Mapping(target = "latitude", ignore = true)
  @Mapping(target = "longitude", ignore = true)
  PetReportDTO toPetReportDTO(LostPetReportDTO lostPetReport);

  @Mapping(source = "reporterId", target = "reportedBy")
  @Mapping(target = "breed", ignore = true)
  @Mapping(target = "contactEmail", ignore = true)
  @Mapping(target = "petName", ignore = true)
  @Mapping(target = "imageUrls", ignore = true)
  PetReportDTO toPetReportDTO(FoundPetReportDTO foundPetReport);
}
