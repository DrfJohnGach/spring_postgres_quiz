package com.drfjohngach.quiz.controller;

import com.drfjohngach.quiz.model.Question;
import com.drfjohngach.quiz.model.QuestionWrapper;
import com.drfjohngach.quiz.model.Quiz;
import com.drfjohngach.quiz.model.Response;
import com.drfjohngach.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping(path = "create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int num, @RequestParam String title){
        return quizService.createQuiz(category, num, title);
    }

    @GetMapping(path = "{quiz}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable Integer quiz){
        return quizService.getQuiz(quiz);
    }

    @PostMapping(path = "submit/{id}")
    public ResponseEntity<Integer> calculateResult(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }
}
