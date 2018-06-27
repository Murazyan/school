package com.example.school1.service.impl;


import com.example.school1.model.Question;
import com.example.school1.reposotory.QuestionRepository;
import com.example.school1.service.QuestionPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionPage {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    @Override
    public Page<Question> findAllPageable(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

}
