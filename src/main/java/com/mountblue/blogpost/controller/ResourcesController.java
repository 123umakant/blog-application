package com.mountblue.blogpost.controller;


import com.mountblue.blogpost.model.Comment;
import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.model.Visitor;
import com.mountblue.blogpost.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ResourcesController {

    private final int POST_INDEX = 0;
    private final int INCREMENT_VALUE_BY_ONE = 1;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    TagsService tagsService;

    @Autowired
    AuthorService authorService;

    @Autowired
    CommentService commentService;

    Visitor author = new Visitor();
    Post post = new Post();
    Comment commentModal = new Comment();

    @GetMapping("/")
    public String homePage(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "author",
            required = false) String author, @RequestParam(value = "sort", required = false) String sort,
                           @RequestParam(value = "publishDate", required = false) String publishDate,
                           @RequestParam(value = "search", required = false) String search, Model model) {

        if (page == null || page < 1) {
            page = 1;
        }
        if (search != null) {

            model.addAttribute("post", postService.getSearchedPost(search));
        } else if (sort != null) {

            model.addAttribute("post", postService.retireAllPostValues(page, sort));
        } else if (author != null && author != "") {

            model.addAttribute("post", postService.fetchDataByAuthorName(author));
        } else if (publishDate != null) {

            model.addAttribute("post", postService.fetchDataByPublishDate(publishDate));
        } else {

            model.addAttribute("post", postService.retireAllPostValues(page));
        }

        model.addAttribute("user", userService.retireAllValues());
        model.addAttribute("page", page);

        return "index";
    }

    @GetMapping("/adminlogin")
    public String adminLogin() {
        return "adminlogin";
    }

    @PostMapping("/adminlogin")
    public String verifyAdminLogin(@RequestParam("userName") String userName,
                                   @RequestParam("password") String password, Model model) {

        author.setName(userName);
        author.setPassword(password);

        boolean value = authorService.verifyAdminDetail(author);
        if (value) {
            model.addAttribute("post", postService.retireAllPost());
            return "adminPage";
        } else {
            return "adminlogin";
        }

    }

    @GetMapping("/userlogin")
    public String userLogin(HttpSession session, Model model) {

        String email = (String) session.getAttribute("email");
        String password = (String) session.getAttribute("password");
       model.addAttribute("email",email);
       model.addAttribute("password",password);
        return "userlogin";
    }

    @PostMapping("/userlogin")
    public String logiDetail(@RequestParam("email") String email, @RequestParam("password") String password, Model model
            , HttpServletRequest request) {

        String emailSession = (String) request.getSession().getAttribute("email");
        if (emailSession == null) {
            request.getSession().setAttribute("email", email);
            request.getSession().setAttribute("password", password);
        }

        author.setEmail(email);
        author.setPassword(password);
        boolean value = authorService.verifyDetail(author);

        model.addAttribute("userName", email);
        model.addAttribute("password", password);
        if (value) {

            List<Post> list = authorService.getAuthorPosts(authorService.getId(email, password));
            if (list.size() == POST_INDEX) {
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


    @GetMapping("/post")
    public String postPage(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        author.setEmail(email);
        author.setPassword(password);
        boolean value = authorService.verifyDetail(author);
        if (value) {
            model.addAttribute("id", authorService.getId(email, password));

            return "post";
        } else {

            return "user_signup";
        }

    }

    @PostMapping("/post")
    public String post(@RequestParam("title") String title, @RequestParam("excerpt") String excerpt,
                       @RequestParam("content") String content, @RequestParam("author") String author,
                       @RequestParam("tags") String tags,
                       @RequestParam("id") String id) {

        post.setId(post.getId() + INCREMENT_VALUE_BY_ONE);
        post.setTitle(title);
        post.setExcerpt(excerpt);
        post.setContent(content);
        post.setAuthor(author);
        post.setVisitor_id(Long.parseLong(id));
        postService.savePost(post);
        tagsService.saveTags(tags, postService.getId());
        return "myblogs";
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
                           @RequestParam("id") long id, @RequestParam("publishedAt") String publishedAt,
                           @RequestParam("createdAt") String createdAt, @RequestParam("isPublished") boolean isPublished
            , @RequestParam("visitor_id") long visitor_id, Model model) throws ParseException {

        System.out.println(isPublished);
        Date datePublishedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(publishedAt);
        Date dateCreatedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdAt);

        post.setId(id);
        post.setTitle(title);
        post.setExcerpt(excerpt);
        post.setContent(content);
        post.setAuthor(author);
        post.setPublishedAt(datePublishedAt);
        post.setPublished(isPublished);
        post.setVisitor_id(visitor_id);
        post.setCreatedAt(dateCreatedAt);
        post.setUpdatedAt(new Date());
        model.addAttribute("updatValues", post);

        return "postedit";
    }

    @PostMapping("editPost")
    public String getPost(@RequestParam("title") String title, @RequestParam("excerpt") String excerpt,
                          @RequestParam("content") String content, @RequestParam("author") String author,
                          @RequestParam("id") long id, @RequestParam("visitor_id") long visitor_id) throws ParseException {
        post.setId(id);
        post.setTitle(title);
        post.setExcerpt(excerpt);
        post.setContent(content);
        post.setAuthor(author);
        post.setVisitor_id(visitor_id);
        post.setUpdatedAt(new Date());
        post.setPublished(false);

        postService.updatePost(post);

        return "myblogs";
    }

    @PostMapping("saveComment")
    public String getComment(@RequestParam("id") String id, @RequestParam("comment") String comment,
                             @RequestParam("name") String name, @RequestParam("email") String email) {

        commentModal.setPostId(Long.parseLong(id));
        commentModal.setComment(comment);
        commentModal.setName(name);
        commentModal.setEmail(email);
        commentService.saveComment(commentModal);
        return "myblogs";
    }

    @ResponseBody
    @GetMapping("getComment")
    public String getComment(@RequestParam("postId") String postId) {
        return commentService.retriveComments(postId);
    }

    @ResponseBody
    @GetMapping("getTags")
    public String getTags(@RequestParam("postId") String postId) {
        return tagsService.retriveTags(postId);
    }

    @GetMapping("deletePost")
    public String getpostId(@RequestParam("postId") String postId) {

        postService.deletePost(postId);
        commentService.deleteComments(postId);
        return "adminPage";
    }

    @GetMapping("logout")
    public String logout() {
        return "redirect:/";
    }
}


