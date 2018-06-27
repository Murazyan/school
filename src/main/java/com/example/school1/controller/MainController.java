package com.example.school1.controller;

import com.example.school1.model.*;
import com.example.school1.reposotory.*;
import com.example.school1.security.CurrentUser;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticlePictureRepository articlePictureRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private QuestionRepository questionRepository;
    @Value("${image.folder}")
    private String imageUploadDir;

    @GetMapping("/image")
    public void getImage(HttpServletResponse response, @RequestParam("fileName") String fileName) throws IOException {
        InputStream in = new FileInputStream(imageUploadDir + fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @PostMapping("/addQuestion")
    public String addquestion(@AuthenticationPrincipal CurrentUser currentUser,
                              @ModelAttribute Question question,
                              @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        String pictureName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File imageDir = new File(imageUploadDir);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        File file = new File(imageUploadDir + pictureName);
        multipartFile.transferTo(file);
        User user = currentUser.getUser();
        question.setUser(user);
        question.setPicUrl(pictureName);
        questionRepository.save(question);
        return "redirect:/blog-sidebar-left";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user) {
        user.setUserType(UserType.GUEST);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/page-login";
    }

    @GetMapping("/")
    public String index2() {
        return "index-03";
    }

    @GetMapping("/page-login")
    public String pageLogin(ModelMap map) {
        map.addAttribute("allUsers", new User());
        return "page-login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal UserDetails userDetails) {
        CurrentUser currentUser = (CurrentUser) userDetails;
        if (currentUser != null) {
            return "redirect:/blog-lg-post-grid";
        } else return "redirect:/page-login";
    }

    @GetMapping("/blog-lg-post-grid")
    public String bloglgpostgrid(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("currentUser", currentUser.getUser());
        modelMap.addAttribute("alquestion", questionRepository.findAllByUserId(currentUser.getUser().getId()));
        return "blog-lg-post-grid";
    }

    @GetMapping("/page-register")
    public String pageRegister(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        return "page-register";
    }

    @GetMapping("/page-sec-navbar")
    public String pagesecnavbar() {
        return "page-sec-navbar";
    }

    @GetMapping("/page-team")
    public String pageTeam() {
        return "page-team";
    }


    @GetMapping("/blog-contained")
    public String blogContained(ModelMap modelMap) {
        modelMap.addAttribute("allLesson", lessonRepository.findAll());
        modelMap.addAttribute("question", new Question());
        return "blog-contained";
    }

    @GetMapping("/add-article")
    public String addArticle(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("currentUser", currentUser.getUser());
        modelMap.addAttribute("article", new Article());
        modelMap.addAttribute("articlePictur", new ArticlePicture());

        return "add-article";
    }

    @PostMapping("/addArticle")
    public String addArticle(@AuthenticationPrincipal CurrentUser currentUser,
                             @ModelAttribute Article article, @ModelAttribute ArticlePicture articlePicture,
                             @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        String pictureName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File imageDir = new File(imageUploadDir);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        File file = new File(imageUploadDir + pictureName);
        multipartFile.transferTo(file);
        User user = currentUser.getUser();
        article.setUser(user);
        articlePicture.setPicUrl(pictureName);
        articleRepository.save(article);
        articlePicture.setArticle(article);
        articlePictureRepository.save(articlePicture);
        return "redirect:/blog-masonry";
    }

    @GetMapping("/blog-sidebar-right")
    public String sidebarRight(ModelMap map, @RequestParam(value = "id") Integer id) {
        map.addAttribute("lessons", lessonRepository.findAll());
        map.addAttribute("questuonBylesson", questionRepository.findAllByLessonId(id));
        return "blog-sidebar-right";
    }

    @GetMapping("/blog-masonry")
    public String allArticle(ModelMap map,
                             @AuthenticationPrincipal CurrentUser currentUser) {

        map.addAttribute("allarticlePicture", articlePictureRepository.findAll());
        return "blog-masonry";
    }

    @GetMapping("/post-gallery")
    public String postgallery(ModelMap map, @AuthenticationPrincipal CurrentUser currentUser, @RequestParam(value = "id") Integer id) {
        map.addAttribute("currentUser", currentUser.getUser());
        map.addAttribute("comment", new Comment());

        map.addAttribute("allarticlePicture", articlePictureRepository.findById(id));
        map.addAttribute("comentByArticleId", commentRepository.findAllByArticlePictureId(id));

        return "post-gallery";
    }

    @GetMapping("/question-readmore")
    public String questionReadMore(ModelMap map, @AuthenticationPrincipal CurrentUser currentUser, @RequestParam(value = "id") Integer id) {
        map.addAttribute("currentUser", currentUser.getUser());
        map.addAttribute("question", new Question());
        map.addAttribute("commentquestion", new Comment());

        map.addAttribute("allquestion", questionRepository.findById(id));
        map.addAttribute("comentByQuestionId", commentRepository.findAllByQuestionId(id));

        return "question-readmore";
    }

    @PostMapping("/add-coment")
    public String addComment(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser, @ModelAttribute Comment comment, @RequestParam(value = "id") Integer id
    ) {
        User user = currentUser.getUser();
        Optional<ArticlePicture> byId = articlePictureRepository.findById(id);
        comment.setUser(user);
        comment.setArticlePicture(byId.get());
        commentRepository.save(comment);
        return "redirect:/blog-masonry";
    }

    @PostMapping("/add-question-coment")
    public String addQuestionComment(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser, @ModelAttribute Comment comment, @RequestParam(value = "id") Integer id
    ) {
        User user = currentUser.getUser();
        Optional<Question> byId = questionRepository.findById(id);
        comment.setUser(user);
        comment.setQuestion(byId.get());
        commentRepository.save(comment);
        return "redirect:/blog-sidebar-left";
    }
}
