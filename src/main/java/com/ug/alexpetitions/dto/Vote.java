package com.ug.alexpetitions.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    private Long id;
    
    @NotBlank
    @Email
    private String email;
    private String name;
    private String userHttpSession;
    private LocalDateTime timestamp;
}
