package com.marcelobatista.dev.helpingPets.src.modules.pets.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marcelobatista.dev.helpingPets.src.modules.pets.domain.PetEntity;
import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetCreateDTO;
import com.marcelobatista.dev.helpingPets.src.modules.pets.dto.PetDTO;

@Mapper(componentModel = "spring")
public interface PetMapper {
  @Mapping(source = "owner.id", target = "ownerId")
  @Mapping(source = "petStatus", target = "status")
  PetDTO toDto(PetEntity petEntity);

  @Mapping(target = "owner", ignore = true)
  @Mapping(source = "status", target = "petStatus")
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  PetEntity toEntity(PetCreateDTO petCreateDTO);

  List<PetDTO> toDtoList(List<PetEntity> petList);
}
