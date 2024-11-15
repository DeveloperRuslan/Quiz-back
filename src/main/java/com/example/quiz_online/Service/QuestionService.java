package com.example.quiz_online.Service;

import com.example.quiz_online.Repository.QuestionRepository;
import com.example.quiz_online.Model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервисный класс для управления вопросами викторины.
 * @author Ruslan Abishev
 */
@Service
@RequiredArgsConstructor // Генерация конструктора с обязательными аргументами
public class QuestionService implements IQuestionService {
    private final QuestionRepository questionRepository; // Репозиторий для работы с вопросами

    @Override
    public Question createQuestion(Question question) {
        // Создание и сохранение нового вопроса
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        // Получение списка всех вопросов
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        // Получение вопроса по его идентификатору
        return questionRepository.findById(id);
    }

    @Override
    public List<String> getAllSubjects() {
        // Получение всех тем для вопросов
        return questionRepository.findDistinctSubject();
    }

    @Override
    public Question updateQuestion(Long id, Question question) throws ChangeSetPersister.NotFoundException {
        // Обновление вопроса по его идентификатору
        Optional<Question> theQuestion = this.getQuestionById(id);
        if (theQuestion.isPresent()) {
            Question updatedQuestion = theQuestion.get();
            // Обновление полей вопроса
            updatedQuestion.setQuestion(question.getQuestion());
            updatedQuestion.setChoices(question.getChoices());
            updatedQuestion.setCorrectAnswers(question.getCorrectAnswers());
            return questionRepository.save(updatedQuestion); // Сохранение обновленного вопроса
        } else {
            // Генерация исключения, если вопрос не найден
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    @Override
    public void deleteQuestion(Long id) {
        // Удаление вопроса по его идентификатору
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> getQuestionsForUser(Integer numOfQuestions, String subject) {
        // Получение определенного количества вопросов
        Pageable pageable = PageRequest.of(0, numOfQuestions);
        return questionRepository.findBySubject(subject, pageable).getContent();
    }
}
