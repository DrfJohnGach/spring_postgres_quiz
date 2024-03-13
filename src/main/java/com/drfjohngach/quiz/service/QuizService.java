package com.drfjohngach.quiz.service;

import com.drfjohngach.quiz.model.Question;
import com.drfjohngach.quiz.model.QuestionWrapper;
import com.drfjohngach.quiz.model.Quiz;
import com.drfjohngach.quiz.dao.QuestionDao;
import com.drfjohngach.quiz.dao.QuizDao;
import com.drfjohngach.quiz.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int num, String title) {

        List<Question> questions = questionDao.findRandomQnsByCategory(category, num);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizDao.save(quiz);

        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer quiz) {

        Optional<Quiz> byId = quizDao.findById(quiz);
        List<Question> dbQuestions = byId.get().getQuestions();
        List<QuestionWrapper> userQuestions = new ArrayList<>();
        for(Question q : dbQuestions){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());

            userQuestions.add(qw);
        }
        return new ResponseEntity<>(userQuestions,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questions = quiz.get().getQuestions();

        int i=0;
        int score=0;
        for(Response response : responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                score++;

            i++;
        }
        return new ResponseEntity<>(score,HttpStatus.OK);
    }
}
