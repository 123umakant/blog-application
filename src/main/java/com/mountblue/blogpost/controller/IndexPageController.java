package com.mountblue.blogpost.controller;

import com.mountblue.blogpost.service.PostService;
import com.mountblue.blogpost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexPageController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @GetMapping("/")
    public String homePage(Model model) {
       // postService.savePost();
        model.addAttribute("user",userService.retireAllValues());
        model.addAttribute("post",postService.retireAllPostValues());
        return "index";
    }
}
