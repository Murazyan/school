package com.example.school1.reposotory;


import com.example.school1.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ArticleRepository extends JpaRepository<Article,Integer> {
    Article findAllByUserId(int id);

}
