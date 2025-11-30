package com.ug.alexpetitions.controller;

import com.ug.alexpetitions.dto.Petition;
import com.ug.alexpetitions.service.PetitionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PetitionController {

    static final String ERROR_MESSAGE = "errorMessage";
    static final String SUCCESS = "success";

    static final String REDIRECT_PETITIONS_VIEW = "redirect:/petitions/view";
    static final String REDIRECT_PETITIONS_CREATE = "redirect:/petitions/create";
    static final String VIEW_PETITIONS = "view-petitions";
    public static final String SEARCH_RESULTS = "search-results";
    public static final String PETITION_DETAIL = "petition-detail";
    public static final String HOME_MAPPING = "/";
    public static final String CREATE_MAPPING = "/petitions/create";
    public static final String VIEW_MAPPING = "/petitions/view";
    public static final String SEARCH_FORM_MAPPING = "/petitions/search";
    public static final String SEARCH_RESULTS_MAPPING = "/petitions/search-results";

    private final PetitionService petitionService;

    @GetMapping(HOME_MAPPING)
    public String home() {
        return "home";
    }

    @GetMapping(CREATE_MAPPING)
    public String showCreateForm() {
        return "create-petition";
    }

    @PostMapping(CREATE_MAPPING)
    public String createPetition(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            RedirectAttributes redirectAttributes) {

        if (title == null || title.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Title is required");
            return REDIRECT_PETITIONS_CREATE;
        }

        if (description == null || description.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Description is required");
            return REDIRECT_PETITIONS_CREATE;
        }

        petitionService.createPetition(title, description);
        redirectAttributes.addFlashAttribute(SUCCESS, "Petition created successfully!");

        return REDIRECT_PETITIONS_VIEW;
    }

    @GetMapping(VIEW_MAPPING)
    public String viewAllPetitions(Model model) {
        List<Petition> petitions = petitionService.getAllPetitions();
        model.addAttribute("petitions", petitions);
        return VIEW_PETITIONS;
    }

    @GetMapping(SEARCH_FORM_MAPPING)
    public String showSearchForm() {
        return "search-petition";
    }

    @GetMapping(SEARCH_RESULTS_MAPPING)
    public String searchPetitions(
            @RequestParam(value = "query", required = false) String query,
            Model model) {

        List<Petition> results = petitionService.searchPetitions(query);
        model.addAttribute("results", results);
        model.addAttribute("query", query);

        return SEARCH_RESULTS;
    }

    @GetMapping("/petitions/{id}")
    public String viewPetitionDetail(@PathVariable("id") Long petitionId, Model model) {
        Petition petition = petitionService.getPetitionById(petitionId);
        if (petition == null) {
            return REDIRECT_PETITIONS_VIEW;
        }
        model.addAttribute("petition", petition);
        return PETITION_DETAIL;
    }

    @PostMapping("/petitions/vote/{id}")
    public String votePetition(
            @PathVariable("id") Long petitionId,
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        String sessionId = session.getId();
        boolean voteAdded = petitionService.addVote(petitionId, email, name, sessionId);

        if (voteAdded) {
            redirectAttributes.addFlashAttribute(SUCCESS, "Vote added successfully!");
        } else {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "You have already voted for this petition.");
        }

        return REDIRECT_PETITIONS_VIEW;
    }
}
