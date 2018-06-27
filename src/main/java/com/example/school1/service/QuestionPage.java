package com.example.school1.service;


import com.example.school1.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionPage {
    Page<Question> findAllPageable(Pageable pageable);
}
