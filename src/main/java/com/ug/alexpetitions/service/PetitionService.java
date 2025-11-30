package com.ug.alexpetitions.service;

import com.ug.alexpetitions.dto.Petition;
import com.ug.alexpetitions.dto.Vote;
import com.ug.alexpetitions.entity.PetitionEntity;
import com.ug.alexpetitions.entity.VoteEntity;
import com.ug.alexpetitions.repository.PetitionRepository;
import com.ug.alexpetitions.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetitionService {

    private final PetitionRepository petitionRepository;
    private final VoteRepository voteRepository;

    @Transactional
    public Petition createPetition(String title, String description) {
        PetitionEntity entity = new PetitionEntity();
        entity.setTitle(title);
        entity.setDescription(description);

        PetitionEntity saved = petitionRepository.save(entity);
        return convertToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<Petition> getAllPetitions() {
        return petitionRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Petition getPetitionById(Long id) {
        return petitionRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Petition> searchPetitions(String query) {
        if (query == null || query.trim().isEmpty()) {
            return petitionRepository.findAll().stream()
                    .map(this::convertToDto)
                    .toList();
        }

        return petitionRepository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional
    public boolean addVote(Long petitionId, String email, String name, String httpSession) {
        if (voteRepository.existsByPetitionIdAndEmail(petitionId, email)) {
            return false;
        }

        if (voteRepository.existsByPetitionIdAndHttpSession(petitionId, httpSession)) {
            return false;
        }

        PetitionEntity petition = petitionRepository.findById(petitionId)
                .orElseThrow(() -> new RuntimeException("Petition not found"));

        VoteEntity vote = new VoteEntity();
        vote.setEmail(email);
        vote.setName(name);
        vote.setHttpSession(httpSession);
        vote.setTimestamp(LocalDateTime.now());
        vote.setPetition(petition);

        voteRepository.save(vote);
        return true;
    }

    private Petition convertToDto(PetitionEntity entity) {
        List<Vote> votes = entity.getVotes().stream()
                .map(this::convertVoteToDto)
                .toList();

        return new Petition(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                votes
        );
    }

    private Vote convertVoteToDto(VoteEntity entity) {
        return new Vote(
                entity.getId(),
                entity.getEmail(),
                entity.getName(),
                entity.getHttpSession(),
                entity.getTimestamp()
        );
    }
}
