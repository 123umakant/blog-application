package com.mountblue.blogpost.controller;


import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.model.Visitor;
import com.mountblue.blogpost.service.AuthorService;
import com.mountblue.blogpost.service.PostService;
import com.mountblue.blogpost.service.TagsService;
import com.mountblue.blogpost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Date;
import java.util.List;

@Controller
public class IndexPageController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    TagsService tagsService;

    @Autowired
    AuthorService authorService;

    Visitor author = new Visitor();
    Post post = new Post();

    @GetMapping("/")
    public String homePage(Model model) {

        //postService.savePost();
        // tagsService.saveTags();
        model.addAttribute("user", userService.retireAllValues());
        model.addAttribute("post", postService.retireAllPostValues());
        model.addAttribute("tags", tagsService.retriveTags());
        return "index";
    }


    @GetMapping("/userlogin")
    public String userLogin() {
        return "userlogin";
    }

    @GetMapping("/post")
    public String postPage() {
        return "post";
    }

    @PostMapping("/post")
    public String post(@RequestParam("title") String title, @RequestParam("excerpt") String excerpt,
                       @RequestParam("content") String content, @RequestParam("author") String author,
                       @RequestParam("id") String id) {

        post.setTitle(title);
        post.setExcerpt(excerpt);
        post.setContent(content);
        post.setAuthor(author);
        post.setVisitor_id(Long.parseLong(id));
        postService.savePost(post);

        return "myblogs";
    }

    @PostMapping
    public String postSubmit() {

        return "";
    }

    @PostMapping("/userlogin")
    public String logiDetail(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        author.setEmail(email);
        author.setPassword(password);
        boolean value = authorService.verifyDetail(author);

        if (value) {

            List<Post> list = authorService.getAuthorPosts(authorService.getId(email, password));
            if (list.size() == 0) {
                model.addAttribute("id", authorService.getId(email, password));
                return "post";
            } else {
                model.addAttribute("post", list);
                return "myblogs";
            }
        } else {

            return "user_signup";
        }

    }

    @GetMapping("/user_signup")
    public String userLoginDetail() {
        return "user_signup";
    }

    @PostMapping("/user_signup")
    public String userSignUp(@RequestParam("name") String name, @RequestParam("email") String email,
                             @RequestParam("password") String password) {

        author.setName(name);
        author.setEmail(email);
        author.setPassword(password);
        authorService.saveLoginDetail(author);
        return "userlogin";
    }

    @GetMapping("viewpost")
    public String viewPostPage(@RequestParam("id") String id, Model model) {

        model.addAttribute("post", postService.retirePostValues(id));

        return "viewpost";
    }

    @PostMapping("edit")
    public String editPost(@RequestParam("title") String title, @RequestParam("excerpt") String excerpt,
                           @RequestParam("content") String content, @RequestParam("author") String author,
                           @RequestParam("id") long id, @RequestParam("publishedAt") Date publishedAt,
                           @RequestParam("createdAt") Date createdAt, @RequestParam("isPublished") boolean isPublished
            , @RequestParam("visitor_id") long visitor_id,Model model)  {

        System.out.println(publishedAt);
        post.setId(id);
        post.setTitle(title);
        post.setExcerpt(excerpt);
        post.setContent(content);
        post.setAuthor(author);
        post.setPublishedAt(publishedAt);
        post.setPublished(isPublished);
        post.setVisitor_id(visitor_id);
        post.setCreatedAt(createdAt);

        model.addAttribute("updatValues",post);

        return "postedit";
    }
}
