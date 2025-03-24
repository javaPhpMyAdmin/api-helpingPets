package com.marcelobatista.dev.helpingPets.src.modules.reports.mapper;

import java.util.List;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.PetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.FoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.LostImageDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.LostPetReportDTO;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PetReportMapper {

  @Mapping(target = "reportType", constant = "LOST")
  @Mapping(source = "reporter.reporterId", target = "reportedBy")
  @Mapping(target = "title", source = "title")
  @Mapping(source = "pet.breed", target = "breed")
  @Mapping(source = "pet.description", target = "description")
  @Mapping(source = "images", target = "imagesLostPet", qualifiedByName = "convertImagesToUrls")
  @Mapping(target = "latitude", ignore = true)
  @Mapping(target = "longitude", ignore = true)
  @Mapping(source = "reporter.contactEmail", target = "contactEmail")
  @Mapping(source = "pet.petName", target = "petName")
  @Mapping(target = "status", source = "metadata.status")
  @Mapping(target = "reportedAt", source = "metadata.reportedAt")
  @Mapping(target = "updatedAt", source = "metadata.updatedAt")
  @Mapping(target = "id", source = "reportId")
  PetReportDTO toPetReportDTO(LostPetReportDTO lostPetReport);

  // Mapeo de FoundPetReportDTO a PetReportDTO
  @Mapping(target = "reportType", constant = "FOUND")
  @Mapping(target = "reportedBy", source = "reporter.reporterId") // Extrae el ID del reporter
  @Mapping(target = "title", source = "title") // No existe en FoundPetReportDTO
  @Mapping(target = "description", source = "image.description")
  @Mapping(target = "latitude", source = "location.coordinatesDTO.latitude")
  @Mapping(target = "longitude", source = "location.coordinatesDTO.longitude")
  @Mapping(target = "status", source = "metadata.status")
  @Mapping(target = "reportedAt", source = "metadata.createdAt")
  @Mapping(target = "updatedAt", source = "metadata.updatedAt")
  @Mapping(target = "imageFoundPet", source = "image.imageUrl") // Si `image` es un solo objeto con una URL
  @Mapping(target = "breed", ignore = true) // No existe en FoundPetReportDTO
  @Mapping(target = "contactEmail", source = "reporter.contactEmail") // No existe en FoundPetReportDTO
  @Mapping(target = "petName", ignore = true) // No existe en FoundPetReportDTO
  @Mapping(target = "id", source = "reportId")
  PetReportDTO toPetReportDTO(FoundPetReportDTO foundPetReport);

  @Named("convertImagesToUrls")
  default List<String> convertImagesToUrls(List<LostImageDTO> images) {
    return images.stream().map(LostImageDTO::imageUrl).toList();
  }
}
