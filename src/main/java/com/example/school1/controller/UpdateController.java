package com.example.school1.controller;

import com.example.school1.model.User;
import com.example.school1.reposotory.UserRepository;
import com.example.school1.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


@Controller
public class UpdateController {

    @Value("${image.folder}")
    private String imageUploadDir;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/updatePicUrl")
    public String updateName(@AuthenticationPrincipal CurrentUser currentUser,
                         @ModelAttribute("user") User user,
                         @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        String pictureName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File imageDir = new File(imageUploadDir);
        if(user.getPicUrl()!=null){
            user1.setPicUrl(user.getPicUrl());
        }
        else if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        File file = new File(imageUploadDir + pictureName);
        multipartFile.transferTo(file);
        user1.setPicUrl(pictureName);
        userRepository.save(user1);
        return "redirect:/page-contact-basic";
    }

    @PostMapping("/updateName")
    public String updateName(@AuthenticationPrincipal CurrentUser currentUser,
                             @ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        user1.setName(user.getName());
        userRepository.save(user1);
        return "redirect:/page-contact-basic";
    }

    @PostMapping("/updateSurname")
    public String updateSurname(@AuthenticationPrincipal CurrentUser currentUser,
                                @ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        user1.setSurname(user.getSurname());
        userRepository.save(user1);
        return "redirect:/page-contact-basic";
    }

    @PostMapping("/updateEmail")
    public String updateEmail(@AuthenticationPrincipal CurrentUser currentUser,
                              @ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        user1.setEmail(user.getEmail());
        userRepository.save(user1);
        return "redirect:/page-contact-basic";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@AuthenticationPrincipal CurrentUser currentUser,
                                 @ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user1);
        return "redirect:/page-contact-basic";
    }
    @GetMapping("/page-contact-basic")
    public String updateUser(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser){
        modelMap.addAttribute("currentUser", currentUser.getUser());
        return "page-contact-basic";

    }
}
