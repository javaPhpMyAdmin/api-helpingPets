package com.marcelobatista.dev.helpingPets.src.modules.reports.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import com.marcelobatista.dev.helpingPets.src.modules.reports.domain.LostPetReport;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.CreateLostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.LostImageDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.LostMetadataDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.LostPetDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.LostPetReportDTO;
import com.marcelobatista.dev.helpingPets.src.modules.reports.dto.LostPetReportDTOs.LostReporterDTO;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface LostPetReportMapper {

  @Mapping(target = "reporter", source = "entity", qualifiedByName = "convertReporter")
  @Mapping(target = "images", source = "entity.imageUrls", qualifiedByName = "convertImages")
  @Mapping(target = "pet", source = "entity", qualifiedByName = "convertPet")
  @Mapping(target = "metadata", source = "entity", qualifiedByName = "convertMetadata")
  @Mapping(target = "reportId", source = "id")
  LostPetReportDTO toDto(LostPetReport entity);

  @Mapping(target = "reporter", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "reportType", ignore = true)
  @Mapping(target = "imageUrls", ignore = true)
  LostPetReport toEntity(CreateLostPetReportDTO dto);

  List<LostPetReportDTO> toDtoList(Page<LostPetReport> entities);

  @Named("convertReporter")
  default LostReporterDTO convertReporter(LostPetReport entity) {
    return new LostReporterDTO(entity.getId(), entity.getContactEmail());
  }

  @Named("convertImages")
  default List<LostImageDTO> convertImages(List<String> imageUrls) {
    return imageUrls.stream().map(LostImageDTO::new).toList();
  }

  @Named("convertPet")
  default LostPetDTO convertPet(LostPetReport entity) {
    return new LostPetDTO(entity.getPetName(), entity.getBreed(), entity.getDescription());
  }

  @Named("convertMetadata")
  default LostMetadataDTO convertMetadata(LostPetReport entity) {
    return new LostMetadataDTO(entity.getReportedAt(), entity.getUpdatedAt(), entity.getStatus());
  }

}
