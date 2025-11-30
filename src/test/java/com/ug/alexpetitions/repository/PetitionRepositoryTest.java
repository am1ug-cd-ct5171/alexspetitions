package com.ug.alexpetitions.repository;

import com.ug.alexpetitions.entity.PetitionEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
class PetitionRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PetitionRepository petitionRepository;

    private PetitionEntity petition1;
    private PetitionEntity petition2;

    @BeforeEach
    void setUp() {
        petition1 = new PetitionEntity();
        petition1.setTitle("Improve Campus Wi-Fi");
        petition1.setDescription("We need better internet connectivity on campus");

        petition2 = new PetitionEntity();
        petition2.setTitle("Extend Library Hours");
        petition2.setDescription("Library should be open 24/7 during exams");

        entityManager.persist(petition1);
        entityManager.persist(petition2);
        entityManager.flush();
    }

    @Test
    void testFindAll() {
        List<PetitionEntity> petitions = petitionRepository.findAll();

        assertNotNull(petitions);
        assertEquals(2, petitions.size());
    }

    @Test
    void testFindById() {
        Optional<PetitionEntity> found = petitionRepository.findById(petition1.getId());

        assertTrue(found.isPresent());
        assertEquals("Improve Campus Wi-Fi", found.get().getTitle());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<PetitionEntity> found = petitionRepository.findById(999L);

        assertFalse(found.isPresent());
    }

    @Test
    void testSave() {
        PetitionEntity newPetition = new PetitionEntity();
        newPetition.setTitle("New Petition");
        newPetition.setDescription("New Description");

        PetitionEntity saved = petitionRepository.save(newPetition);

        assertNotNull(saved.getId());
        assertEquals("New Petition", saved.getTitle());
    }

    @Test
    void testFindByTitleContainingIgnoreCase() {
        List<PetitionEntity> results = petitionRepository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase("wi-fi", "wi-fi");

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Improve Campus Wi-Fi", results.get(0).getTitle());
    }

    @Test
    void testFindByDescriptionContainingIgnoreCase() {
        List<PetitionEntity> results = petitionRepository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase("24/7", "24/7");

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Extend Library Hours", results.get(0).getTitle());
    }

    @Test
    void testFindByTitleOrDescriptionNoResults() {
        List<PetitionEntity> results = petitionRepository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase("nonexistent", "nonexistent");

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void testFindByTitleOrDescriptionCaseInsensitive() {
        List<PetitionEntity> results = petitionRepository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase("LIBRARY", "LIBRARY");

        assertNotNull(results);
        assertEquals(1, results.size());
    }

    @Test
    void testDelete() {
        petitionRepository.deleteById(petition1.getId());
        entityManager.flush();

        Optional<PetitionEntity> deleted = petitionRepository.findById(petition1.getId());
        assertFalse(deleted.isPresent());
    }

    @Test
    void testUpdate() {
        petition1.setTitle("Updated Title");
        petitionRepository.save(petition1);
        entityManager.flush();

        Optional<PetitionEntity> updated = petitionRepository.findById(petition1.getId());
        assertTrue(updated.isPresent());
        assertEquals("Updated Title", updated.get().getTitle());
    }
}
