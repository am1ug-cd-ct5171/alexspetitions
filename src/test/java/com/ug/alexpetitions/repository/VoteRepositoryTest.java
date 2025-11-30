package com.ug.alexpetitions.repository;

import com.ug.alexpetitions.entity.PetitionEntity;
import com.ug.alexpetitions.entity.VoteEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
class VoteRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private VoteRepository voteRepository;

    private PetitionEntity petition;
    private VoteEntity vote;

    @BeforeEach
    void setUp() {
        petition = new PetitionEntity();
        petition.setTitle("Test Petition");
        petition.setDescription("Test Description");
        entityManager.persist(petition);

        vote = new VoteEntity();
        vote.setEmail("test@example.com");
        vote.setName("Test User");
        vote.setHttpSession("session123");
        vote.setTimestamp(LocalDateTime.now());
        vote.setPetition(petition);
        entityManager.persist(vote);
        entityManager.flush();
    }

    @Test
    void testFindByPetitionIdAndEmail() {
        Optional<VoteEntity> found = voteRepository.findByPetitionIdAndEmail(
                petition.getId(), "test@example.com");

        assertTrue(found.isPresent());
        assertEquals("Test User", found.get().getName());
    }

    @Test
    void testFindByPetitionIdAndEmailNotFound() {
        Optional<VoteEntity> found = voteRepository.findByPetitionIdAndEmail(
                petition.getId(), "notfound@example.com");

        assertFalse(found.isPresent());
    }

    @Test
    void testExistsByPetitionIdAndEmail() {
        boolean exists = voteRepository.existsByPetitionIdAndEmail(
                petition.getId(), "test@example.com");

        assertTrue(exists);
    }

    @Test
    void testExistsByPetitionIdAndEmailNotFound() {
        boolean exists = voteRepository.existsByPetitionIdAndEmail(
                petition.getId(), "notfound@example.com");

        assertFalse(exists);
    }

    @Test
    void testExistsByPetitionIdAndHttpSession() {
        boolean exists = voteRepository.existsByPetitionIdAndHttpSession(
                petition.getId(), "session123");

        assertTrue(exists);
    }

    @Test
    void testExistsByPetitionIdAndHttpSessionNotFound() {
        boolean exists = voteRepository.existsByPetitionIdAndHttpSession(
                petition.getId(), "session999");

        assertFalse(exists);
    }

    @Test
    void testSaveVote() {
        VoteEntity newVote = new VoteEntity();
        newVote.setEmail("new@example.com");
        newVote.setName("New User");
        newVote.setHttpSession("session456");
        newVote.setTimestamp(LocalDateTime.now());
        newVote.setPetition(petition);

        VoteEntity saved = voteRepository.save(newVote);

        assertNotNull(saved.getId());
        assertEquals("new@example.com", saved.getEmail());
    }

    @Test
    void testDeleteVote() {
        voteRepository.deleteById(vote.getId());
        entityManager.flush();

        Optional<VoteEntity> deleted = voteRepository.findById(vote.getId());
        assertFalse(deleted.isPresent());
    }

    @Test
    void testFindAll() {
        assertEquals(1, voteRepository.findAll().size());
    }
}
