package com.example.quiz_online.Model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @author Ruslan Abishev
 */
@Getter
@Setter
@Entity
public class Question {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String question;
    @NotBlank
    private String subject;
    @NotBlank
    private String questionType;


    @ElementCollection
    private List<String> choices;


    @ElementCollection
    private List<String> correctAnswers;



}
