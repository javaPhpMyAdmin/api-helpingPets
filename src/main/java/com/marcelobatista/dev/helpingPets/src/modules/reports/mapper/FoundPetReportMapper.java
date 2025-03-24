package com.marcelobatista.dev.helpingPets.src.modules.reports.mapper;

import java.util.List;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.FoundPetReport;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.CoordinatesDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.CreateFoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.FoundPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.ImageDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.LocationDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.MetadataDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.FoundPetReportDTOs.ReporterDTO;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface FoundPetReportMapper {
  @Mapping(target = "reporter", source = "entity.reporter", qualifiedByName = "convertReporter")
  @Mapping(target = "image.imageUrl", source = "imageUrl")
  @Mapping(target = "image.description", source = "description")
  @Mapping(target = "location", source = "entity", qualifiedByName = "convertLocation")
  @Mapping(target = "metadata", source = "entity", qualifiedByName = "convertMetadata")
  @Mapping(target = "reportId", source = "id")
  @Mapping(target = "title", source = "title")
  FoundPetReportDTO toDto(FoundPetReport entity);

  @Mapping(target = "status", source = "foundPetStatus")
  @Mapping(target = "reporter.id", ignore = true)
  @Mapping(target = "reportType", ignore = true)
  @Mapping(target = "imageUrl", ignore = true)
  @Mapping(target = "latitude", source = "location.coordinatesDTO.latitude")
  @Mapping(target = "longitude", source = "location.coordinatesDTO.longitude")
  @Mapping(target = "description", ignore = true)
  FoundPetReport toEntity(CreateFoundPetReportDTO dto);

  List<FoundPetReportDTO> toDtoList(List<FoundPetReport> entities);

  // Métodos de ayuda
  @Named("convertReporter")
  default ReporterDTO convertReporter(User reporter) {
    return new ReporterDTO(reporter.getId(), reporter.getEmail());
  }

  @Named("convertImage")
  default ImageDTO convertImage(FoundPetReport entity) {
    return new ImageDTO(entity.getImageUrl(), entity.getDescription());
  }

  @Named("convertLocation")
  default LocationDTO convertLocation(FoundPetReport entity) {
    return new LocationDTO(new CoordinatesDTO(entity.getLatitude(), entity.getLongitude()));
  }

  @Named("convertMetadata")
  default MetadataDTO convertMetadata(FoundPetReport entity) {
    return new MetadataDTO(entity.getStatus(), entity.getCreatedAt(), entity.getUpdatedAt());
  }
}
