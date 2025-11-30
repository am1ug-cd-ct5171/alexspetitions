package com.ug.alexpetitions.repository;

import com.ug.alexpetitions.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {

    boolean existsByPetitionIdAndEmail(Long petitionId, String email);

    boolean existsByPetitionIdAndHttpSession(Long petitionId, String httpSession);
}
