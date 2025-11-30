package com.ug.alexpetitions.service;

import com.ug.alexpetitions.dto.Petition;
import com.ug.alexpetitions.entity.PetitionEntity;
import com.ug.alexpetitions.entity.VoteEntity;
import com.ug.alexpetitions.repository.PetitionRepository;
import com.ug.alexpetitions.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetitionServiceTest {

    @Mock
    private PetitionRepository petitionRepository;

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private PetitionService petitionService;

    private PetitionEntity petitionEntity;
    private VoteEntity voteEntity;

    @BeforeEach
    void setUp() {
        petitionEntity = new PetitionEntity();
        petitionEntity.setId(1L);
        petitionEntity.setTitle("Test Petition");
        petitionEntity.setDescription("Test Description");
        petitionEntity.setVotes(new ArrayList<>());

        voteEntity = new VoteEntity();
        voteEntity.setId(1L);
        voteEntity.setEmail("test@example.com");
        voteEntity.setName("Test User");
        voteEntity.setHttpSession("session123");
        voteEntity.setTimestamp(LocalDateTime.now());
        voteEntity.setPetition(petitionEntity);
    }

    @Test
    void testCreatePetition() {
        when(petitionRepository.save(any(PetitionEntity.class))).thenReturn(petitionEntity);

        Petition result = petitionService.createPetition("Test Petition", "Test Description");

        assertNotNull(result);
        assertEquals("Test Petition", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        verify(petitionRepository, times(1)).save(any(PetitionEntity.class));
    }

    @Test
    void testGetAllPetitions() {
        List<PetitionEntity> entities = Arrays.asList(petitionEntity);
        when(petitionRepository.findAll()).thenReturn(entities);

        List<Petition> result = petitionService.getAllPetitions();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Petition", result.get(0).getTitle());
        verify(petitionRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPetitionsEmpty() {
        when(petitionRepository.findAll()).thenReturn(new ArrayList<>());

        List<Petition> result = petitionService.getAllPetitions();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(petitionRepository, times(1)).findAll();
    }

    @Test
    void testGetPetitionById() {
        when(petitionRepository.findById(1L)).thenReturn(Optional.of(petitionEntity));

        Petition result = petitionService.getPetitionById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Petition", result.getTitle());
        verify(petitionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPetitionByIdNotFound() {
        when(petitionRepository.findById(999L)).thenReturn(Optional.empty());

        Petition result = petitionService.getPetitionById(999L);

        assertNull(result);
        verify(petitionRepository, times(1)).findById(999L);
    }

    @Test
    void testSearchPetitionsWithQuery() {
        List<PetitionEntity> entities = Arrays.asList(petitionEntity);
        when(petitionRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                anyString(), anyString())).thenReturn(entities);

        List<Petition> result = petitionService.searchPetitions("Test");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Petition", result.get(0).getTitle());
        verify(petitionRepository, times(1))
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase("Test", "Test");
    }

    @Test
    void testSearchPetitionsWithEmptyQuery() {
        List<PetitionEntity> entities = Arrays.asList(petitionEntity);
        when(petitionRepository.findAll()).thenReturn(entities);

        List<Petition> result = petitionService.searchPetitions("");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(petitionRepository, times(1)).findAll();
    }

    @Test
    void testSearchPetitionsWithNullQuery() {
        List<PetitionEntity> entities = Arrays.asList(petitionEntity);
        when(petitionRepository.findAll()).thenReturn(entities);

        List<Petition> result = petitionService.searchPetitions(null);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(petitionRepository, times(1)).findAll();
    }

    @Test
    void testAddVoteSuccess() {
        when(voteRepository.existsByPetitionIdAndEmail(anyLong(), anyString())).thenReturn(false);
        when(voteRepository.existsByPetitionIdAndHttpSession(anyLong(), anyString())).thenReturn(false);
        when(petitionRepository.findById(1L)).thenReturn(Optional.of(petitionEntity));
        when(voteRepository.save(any(VoteEntity.class))).thenReturn(voteEntity);

        boolean result = petitionService.addVote(1L, "test@example.com", "Test User", "session123");

        assertTrue(result);
        verify(voteRepository, times(1)).existsByPetitionIdAndEmail(1L, "test@example.com");
        verify(voteRepository, times(1)).existsByPetitionIdAndHttpSession(1L, "session123");
        verify(voteRepository, times(1)).save(any(VoteEntity.class));
    }

    @Test
    void testAddVoteDuplicateEmail() {
        when(voteRepository.existsByPetitionIdAndEmail(anyLong(), anyString())).thenReturn(true);

        boolean result = petitionService.addVote(1L, "test@example.com", "Test User", "session123");

        assertFalse(result);
        verify(voteRepository, times(1)).existsByPetitionIdAndEmail(1L, "test@example.com");
        verify(voteRepository, never()).save(any(VoteEntity.class));
    }

    @Test
    void testAddVoteDuplicateSession() {
        when(voteRepository.existsByPetitionIdAndEmail(anyLong(), anyString())).thenReturn(false);
        when(voteRepository.existsByPetitionIdAndHttpSession(anyLong(), anyString())).thenReturn(true);

        boolean result = petitionService.addVote(1L, "test@example.com", "Test User", "session123");

        assertFalse(result);
        verify(voteRepository, times(1)).existsByPetitionIdAndHttpSession(1L, "session123");
        verify(voteRepository, never()).save(any(VoteEntity.class));
    }

    @Test
    void testAddVotePetitionNotFound() {
        when(voteRepository.existsByPetitionIdAndEmail(anyLong(), anyString())).thenReturn(false);
        when(voteRepository.existsByPetitionIdAndHttpSession(anyLong(), anyString())).thenReturn(false);
        when(petitionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> 
                petitionService.addVote(999L, "test@example.com", "Test User", "session123"));

        verify(voteRepository, never()).save(any(VoteEntity.class));
    }

    @Test
    void testConvertToDtoWithVotes() {
        petitionEntity.getVotes().add(voteEntity);
        when(petitionRepository.findById(1L)).thenReturn(Optional.of(petitionEntity));

        Petition result = petitionService.getPetitionById(1L);

        assertNotNull(result);
        assertEquals(1, result.getVoteCount());
        assertEquals(1, result.getVotes().size());
        assertEquals("test@example.com", result.getVotes().get(0).getEmail());
    }
}
