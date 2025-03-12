package com.marcelobatista.dev.helpingPets.src.modules.reports.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.PetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.FoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.LostPetReportDTO;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PetReportMapper {

  // @Mapping(target = "title", ignore = true)
  // @Mapping(source = "reporterId", target = "reportedBy")
  // @Mapping(target = "latitude", ignore = true)
  // @Mapping(target = "longitude", ignore = true)
  // PetReportDTO toPetReportDTO(LostPetReportDTO lostPetReport);

  // @Mapping(target = "reportedBy", expression =
  // "java(foundPetReport.getReporter() != null ?
  // foundPetReport.getReporter().getId() : null)" )
  // @Mapping(target = "breed", ignore = true)
  // @Mapping(target = "contactEmail", ignore = true)
  // @Mapping(target = "petName", ignore = true)
  // @Mapping(target = "imageUrls", ignore = true)
  // PetReportDTO toPetReportDTO(FoundPetReportDTO foundPetReport);
  // Mapeo de LostPetReportDTO a PetReportDTO
  @Mapping(target = "reportType", constant = "LOST")
  @Mapping(source = "reporterId", target = "reportedBy")
  @Mapping(target = "title", ignore = true)
  @Mapping(source = "breed", target = "breed")
  @Mapping(source = "description", target = "description")
  @Mapping(source = "imageUrls", target = "imageUrls")
  @Mapping(target = "latitude", ignore = true)
  @Mapping(target = "longitude", ignore = true)
  @Mapping(source = "contactEmail", target = "contactEmail")
  @Mapping(source = "petName", target = "petName")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "reportedAt", target = "reportedAt")
  @Mapping(source = "updatedAt", target = "updatedAt")
  PetReportDTO toPetReportDTO(LostPetReportDTO lostPetReport);

  // Mapeo de FoundPetReportDTO a PetReportDTO
  @Mapping(target = "reportType", constant = "FOUND")
  @Mapping(target = "reportedBy", source = "reporter.reporterId") // Extrae el ID del reporter
  @Mapping(target = "title", ignore = true) // No existe en FoundPetReportDTO
  @Mapping(target = "description", source = "image.description")
  @Mapping(target = "latitude", source = "location.coordinatesDTO.latitude")
  @Mapping(target = "longitude", source = "location.coordinatesDTO.longitude")
  @Mapping(target = "status", source = "metadata.status")
  @Mapping(target = "reportedAt", source = "metadata.createdAt")
  @Mapping(target = "updatedAt", source = "metadata.updatedAt")
  @Mapping(target = "imageUrls", ignore = true) // Si `image` es un solo objeto con una URL
  @Mapping(target = "breed", ignore = true) // No existe en FoundPetReportDTO
  @Mapping(target = "contactEmail", ignore = true) // No existe en FoundPetReportDTO
  @Mapping(target = "petName", ignore = true) // No existe en FoundPetReportDTO
  PetReportDTO toPetReportDTO(FoundPetReportDTO foundPetReport);
}
