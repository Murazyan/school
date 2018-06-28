package com.example.school1.reposotory;


import com.example.school1.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findAllByUserId(Integer id);
    List<Question> findAllByLessonId(Integer id);

}
