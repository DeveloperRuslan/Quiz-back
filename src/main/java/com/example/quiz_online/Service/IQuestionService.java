package com.example.quiz_online.Service;

import com.example.quiz_online.Model.Question;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

/**
 * @author Ruslan Abishev
 */

public interface IQuestionService {

    Question createQuestion(Question question);

    List<Question> getAllQuestions();

    Optional<Question> getQuestionById(Long id);

    List<String> getAllSubjects();

    Question updateQuestion(Long id, Question question) throws ChangeSetPersister.NotFoundException;

    void  deleteQuestion(Long id);

    List<Question> getQuestionsForUser(Integer numOfQuestions, String subject);


}