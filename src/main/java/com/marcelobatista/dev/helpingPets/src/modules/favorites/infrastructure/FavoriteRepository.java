package com.marcelobatista.dev.helpingPets.src.modules.favorites.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marcelobatista.dev.helpingPets.src.modules.favorites.domain.FavoriteEntity;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

  List<FavoriteEntity> findByUserId(Long userId);

  Optional<FavoriteEntity> findByUserIdAndPetId(Long userId, Long petId);

  @Modifying
  @Query(value = "DELETE FROM favorites WHERE user_id= :userId and pet_id= :petId", nativeQuery = true)
  void deleteByUserIdAndPetId(@Param("userId") Long userId, @Param("petId") Long petId);
}
