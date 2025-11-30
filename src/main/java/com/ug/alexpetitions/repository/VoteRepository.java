package com.ug.alexpetitions.repository;

import com.ug.alexpetitions.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {

    boolean existsByPetitionIdAndEmail(Long petitionId, String email);

    boolean existsByPetitionIdAndHttpSession(Long petitionId, String httpSession);

    Optional<VoteEntity> findByPetitionIdAndEmail(Long petitionId, String email);
}
