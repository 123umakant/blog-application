package com.mountblue.blogpost.controller;


import com.mountblue.blogpost.service.PostService;
import com.mountblue.blogpost.service.TagsService;
import com.mountblue.blogpost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexPageController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    TagsService tagsService;

    @GetMapping("/")
    public String homePage(Model model) {
        System.out.println("hello");
        //postService.savePost();
       // tagsService.saveTags();
        model.addAttribute("user",userService.retireAllValues());
        model.addAttribute("post",postService.retireAllPostValues());
        model.addAttribute("tags",tagsService.retriveTags());
        return "index";
    }

    @GetMapping("/userlogin")
    public String userLogin(Model model){
        return "userlogin";
    }
    @PostMapping("/userlogin")
    public String userLoginDetail(@RequestParam("email") String email,@RequestParam("password") String password){


        return "userlogin";
    }
    @GetMapping("/user_signup")
    public String userSignUp(Model model){
        return "user_signup";
    }
}
