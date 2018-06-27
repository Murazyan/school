package com.example.school1.reposotory;

import com.example.school1.model.Comment;

import com.example.school1.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
        List<Comment> findAllByArticlePictureId(int id);
        List<Comment> findAllByQuestionId(int id);

}
