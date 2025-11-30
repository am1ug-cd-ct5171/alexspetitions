package com.ug.alexpetitions.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "petitions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "petition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteEntity> votes = new ArrayList<>();

    public void addVote(VoteEntity vote) {
        votes.add(vote);
        vote.setPetition(this);
    }

    public void removeVote(VoteEntity vote) {
        votes.remove(vote);
        vote.setPetition(null);
    }

    public int getVoteCount() {
        return votes != null ? votes.size() : 0;
    }
}
