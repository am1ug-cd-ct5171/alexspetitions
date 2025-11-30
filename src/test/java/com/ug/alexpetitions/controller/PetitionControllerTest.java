package com.ug.alexpetitions.controller;

import com.ug.alexpetitions.dto.Petition;
import com.ug.alexpetitions.service.PetitionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ug.alexpetitions.controller.PetitionController.CREATE_MAPPING;
import static com.ug.alexpetitions.controller.PetitionController.VIEW_MAPPING;
import static com.ug.alexpetitions.controller.PetitionController.SEARCH_FORM_MAPPING;
import static com.ug.alexpetitions.controller.PetitionController.SEARCH_RESULTS_MAPPING;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetitionController.class)
class PetitionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PetitionService petitionService;

    private Petition petition;
    private List<Petition> petitions;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        petition = new Petition(1L, "Test Petition", "Test Description", new ArrayList<>());
        petitions = Arrays.asList(petition);
        session = new MockHttpSession();
    }

    @Test
    void testHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get(CREATE_MAPPING))
                .andExpect(status().isOk())
                .andExpect(view().name("create-petition"));
    }

    @Test
    void testCreatePetitionSuccess() throws Exception {
        when(petitionService.createPetition(anyString(), anyString())).thenReturn(petition);

        mockMvc.perform(post(CREATE_MAPPING)
                        .param("title", "Test Petition")
                        .param("description", "Test Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(VIEW_MAPPING))
                .andExpect(flash().attribute("success", "Petition created successfully!"));

        verify(petitionService, times(1)).createPetition("Test Petition", "Test Description");
    }

    @Test
    void testCreatePetitionEmptyTitle() throws Exception {
        mockMvc.perform(post(CREATE_MAPPING)
                        .param("title", "")
                        .param("description", "Test Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(CREATE_MAPPING))
                .andExpect(flash().attribute("errorMessage", "Title is required"));

        verify(petitionService, never()).createPetition(anyString(), anyString());
    }

    @Test
    void testCreatePetitionNullTitle() throws Exception {
        mockMvc.perform(post(CREATE_MAPPING)
                        .param("description", "Test Description"))
                .andExpect(status().isBadRequest());

        verify(petitionService, never()).createPetition(anyString(), anyString());
    }

    @Test
    void testCreatePetitionEmptyDescription() throws Exception {
        mockMvc.perform(post(CREATE_MAPPING)
                        .param("title", "Test Title")
                        .param("description", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(CREATE_MAPPING))
                .andExpect(flash().attribute("errorMessage", "Description is required"));

        verify(petitionService, never()).createPetition(anyString(), anyString());
    }

    @Test
    void testViewAllPetitions() throws Exception {
        when(petitionService.getAllPetitions()).thenReturn(petitions);

        mockMvc.perform(get(VIEW_MAPPING))
                .andExpect(status().isOk())
                .andExpect(view().name("view-petitions"))
                .andExpect(model().attributeExists("petitions"))
                .andExpect(model().attribute("petitions", petitions));

        verify(petitionService, times(1)).getAllPetitions();
    }

    @Test
    void testShowSearchForm() throws Exception {
        mockMvc.perform(get(SEARCH_FORM_MAPPING))
                .andExpect(status().isOk())
                .andExpect(view().name("search-petition"));
    }

    @Test
    void testSearchPetitions() throws Exception {
        when(petitionService.searchPetitions("test")).thenReturn(petitions);

        mockMvc.perform(get(SEARCH_RESULTS_MAPPING)
                        .param("query", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("search-results"))
                .andExpect(model().attributeExists("results"))
                .andExpect(model().attribute("results", petitions))
                .andExpect(model().attribute("query", "test"));

        verify(petitionService, times(1)).searchPetitions("test");
    }

    @Test
    void testSearchPetitionsWithoutQuery() throws Exception {
        when(petitionService.searchPetitions(null)).thenReturn(petitions);

        mockMvc.perform(get(SEARCH_RESULTS_MAPPING))
                .andExpect(status().isOk())
                .andExpect(view().name("search-results"))
                .andExpect(model().attributeExists("results"));

        verify(petitionService, times(1)).searchPetitions(null);
    }

    @Test
    void testViewPetitionDetail() throws Exception {
        when(petitionService.getPetitionById(1L)).thenReturn(petition);

        mockMvc.perform(get("/petitions/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("petition-detail"))
                .andExpect(model().attributeExists("petition"))
                .andExpect(model().attribute("petition", petition));

        verify(petitionService, times(1)).getPetitionById(1L);
    }

    @Test
    void testViewPetitionDetailNotFound() throws Exception {
        when(petitionService.getPetitionById(999L)).thenReturn(null);

        mockMvc.perform(get("/petitions/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(VIEW_MAPPING));

        verify(petitionService, times(1)).getPetitionById(999L);
    }

    @Test
    void testVotePetitionSuccess() throws Exception {
        when(petitionService.addVote(anyLong(), anyString(), anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/petitions/vote/1")
                        .param("email", "test@example.com")
                        .param("name", "Test User")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(VIEW_MAPPING))
                .andExpect(flash().attribute("success", "Vote added successfully!"));

        verify(petitionService, times(1)).addVote(eq(1L), eq("test@example.com"), 
                eq("Test User"), anyString());
    }

    @Test
    void testVotePetitionDuplicate() throws Exception {
        when(petitionService.addVote(anyLong(), anyString(), anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/petitions/vote/1")
                        .param("email", "test@example.com")
                        .param("name", "Test User")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(VIEW_MAPPING))
                .andExpect(flash().attribute("errorMessage", "You have already voted for this petition."));

        verify(petitionService, times(1)).addVote(eq(1L), eq("test@example.com"), 
                eq("Test User"), anyString());
    }
}
