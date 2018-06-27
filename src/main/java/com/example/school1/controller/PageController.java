package com.example.school1.controller;


import com.example.school1.model.Question;
import com.example.school1.reposotory.LessonRepository;
import com.example.school1.reposotory.QuestionRepository;
import com.example.school1.security.CurrentUser;
import com.example.school1.service.QuestionPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PageController {

    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private LessonRepository lessonRepository;

    private final QuestionPage questionPage;

    public PageController(QuestionPage questionPage) {
        this.questionPage = questionPage;
    }

    @GetMapping("/blog-sidebar-left")
    public String indexPage(ModelMap map,
                                  @AuthenticationPrincipal CurrentUser currentUser,
                                  @RequestParam("pageSize") Optional<Integer> pageSize,
                                  @RequestParam("page") Optional<Integer> page) {
        map.addAttribute("lessons", lessonRepository.findAll());
        map.addAttribute("isLoggedIn", currentUser  != null);
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        Page<Question> questions = questionPage.findAllPageable(PageRequest.of(evalPage, evalPageSize));
        map.addAttribute("allquestion", questions);
        map.addAttribute("selectedPageSize", evalPageSize);
        map.addAttribute("Lessons",new Question());
        return "blog-sidebar-left";
    }



}
