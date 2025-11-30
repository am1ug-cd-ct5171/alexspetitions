package com.ug.alexpetitions.repository;

import com.ug.alexpetitions.entity.PetitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetitionRepository extends JpaRepository<PetitionEntity, Long> {

    List<PetitionEntity> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
}
