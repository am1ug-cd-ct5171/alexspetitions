package com.ug.alexpetitions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Petition {

    private Long id;
    private String title;
    private String description;
    private List<Vote> votes;
    private Integer voteCount;

    public Petition(Long id, String title, String description, List<Vote> votes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.votes = votes;
        this.voteCount = votes != null ? votes.size() : 0;
    }
}
