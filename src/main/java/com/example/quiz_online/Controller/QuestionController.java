package com.example.quiz_online.Controller;

import com.example.quiz_online.Model.Question;
import com.example.quiz_online.Service.IQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
/**
 * @автор Руслан Абишев
 */

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuestionController {
    private final IQuestionService questionService;

    // Метод для создания нового вопроса
    @PostMapping("/create-new-question")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question){
        Question createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(CREATED).body(createdQuestion);
    }
    // Метод для получения всех вопросов
    @GetMapping("/all-questions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }
    // Метод для получения вопроса по идентификатору
    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Question> theQuestion = questionService.getQuestionById(id);
        if (theQuestion.isPresent()){
            return ResponseEntity.ok(theQuestion.get());
        }else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }
    // Метод для обновления вопроса
    @PutMapping("/question/{id}/update")
    public ResponseEntity<Question> updateQuestion(
            @PathVariable Long id, @RequestBody Question question) throws ChangeSetPersister.NotFoundException {
        Question updatedQuestion = questionService.updateQuestion(id, question);
        return ResponseEntity.ok(updatedQuestion);
    }
    // Метод для удаления вопроса
    @DeleteMapping("/question/{id}/delete")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
    // Метод для получения всех тем
    @GetMapping("/subjects")
    public ResponseEntity<List<String>> getAllSubjects(){
        List<String> subjects = questionService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }
    // Метод для получения случайных вопросов для пользователя
    @GetMapping("/quiz/fetch-questions-for-user")
    public ResponseEntity<List<Question>> getQuestionsForUser(
            @RequestParam Integer numOfQuestions, @RequestParam String subject){
        List<Question> allQuestions = questionService.getQuestionsForUser(numOfQuestions, subject);
        // Перемешиваем список вопросов
        List<Question> mutableQuestions = new ArrayList<>(allQuestions);
        Collections.shuffle(mutableQuestions);
        // Ограничиваем выборку доступными вопросами
        int availableQuestions = Math.min(numOfQuestions, mutableQuestions.size());
        List<Question> randomQuestions = mutableQuestions.subList(0, availableQuestions);// Получаем подсписок случайных вопросов
        return ResponseEntity.ok(randomQuestions);
    }
}
