package com.mountblue.blogpost.controller;


import com.mountblue.blogpost.dto.*;
import com.mountblue.blogpost.model.Author;
import com.mountblue.blogpost.model.Comment;
import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.model.Tag;
import com.mountblue.blogpost.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/main")
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

    Author author = new Author();
    Post post = new Post();
    Comment commentModal = new Comment();

    @GetMapping("")
    public ResponseEntity<FilterAndSearchPostDto> homePage(@RequestParam(value = "page", required = false) Integer page, @RequestParam
            (value = "sort", required = false) String sort,
                                                           @RequestParam(value = "publishDate", required = false) String publishDate,
                                                           @RequestParam(value = "search", required = false) String search,
                                                           @RequestParam(value = "tagSearch", required = false) String tagSearch,
                                                           @RequestParam(value = "tagSearchId", required = false) String tagSearchId,
                                                           Model model) {

        FilterAndSearchPostDto filterAndSearchPostDto = new FilterAndSearchPostDto();

        HashMap hashMap = new HashMap();

        if (page == null || page < 1) {
            page = 1;
        }
        if (search != null && search != "" && sort != null && !sort.equals("Sort") && publishDate != null) {
            System.out.println("inside all search");
            model.addAttribute("post", postService.getSearchedPostAll(page, search, sort, publishDate));
            hashMap.put("post", postService.getSearchedPostAll(page, search, sort, publishDate));
        }
        if (search != null && search != "") {
            System.out.println("inside search");
            model.addAttribute("post", postService.getSearchedPost(search));
            hashMap.put("post", postService.getSearchedPost(search));
        } else if (sort != null && !sort.equals("Sort")) {
            System.out.println(sort);
            System.out.println("inside sort");
            model.addAttribute("post", postService.retireAllPostValues(page, sort));
            hashMap.put("post", postService.retireAllPostValues(page, sort));
        } else if (publishDate != null) {
            System.out.println("inside date");
            model.addAttribute("post", postService.fetchDataByPublishDate(publishDate));
            hashMap.put("post", postService.fetchDataByPublishDate(publishDate));
        } else if (tagSearch != null) {
            System.out.println(tagSearch);
            model.addAttribute("post", tagsService.retireAllPostValues(tagSearchId));
            hashMap.put("post", model.addAttribute("post", tagsService.retireAllPostValues(tagSearchId)));
        } else {

            model.addAttribute("post", postService.retireAllPostValues(page));
            hashMap.put("post", postService.retireAllPostValues(page));
        }

        model.addAttribute("tags", tagsService.retireAllValues());
        model.addAttribute("page", page);
        hashMap.put("tags", tagsService.retireAllValues());
        hashMap.put("page", page);

        filterAndSearchPostDto.setHashMap(hashMap);
        return new ResponseEntity(filterAndSearchPostDto, HttpStatus.OK);
    }

    @PostMapping("/get/mypost")
    public ResponseEntity<List<Post>> postPage(@RequestBody RegisterDto registerDto) {

        List<Post> posts = authorService.getPosts(registerDto.getEmail(), registerDto.getPassword());
        return new ResponseEntity(posts, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<ResponseStatusDto> post(@RequestBody PostDto postDto) {

        postService.savePost(postDto);
        ResponseStatusDto responseStatusDto = new ResponseStatusDto();
        responseStatusDto.setStatus("Post Saved");
        return new ResponseEntity(responseStatusDto, HttpStatus.OK);
    }


    @PostMapping("editPost")
    public ResponseEntity<ResponseStatusDto> getPost(@RequestBody PostDto postDto) throws ParseException {

        ResponseStatusDto responseStatusDto = new ResponseStatusDto();
        int rowEffected = postService.updatePost(postDto);
        if (rowEffected > 0) {
            responseStatusDto.setStatus("Post Updated Successfully");
        } else {
            responseStatusDto.setStatus("No Post Available");
        }


        return new ResponseEntity(responseStatusDto, HttpStatus.OK);
    }

    @PostMapping("deletePost")
    public ResponseEntity<ResponseStatusDto> deletePost(@RequestBody PostDto postDto) {
        ResponseStatusDto responseStatusDto = new ResponseStatusDto();
        postService.deletePost(postDto.getId());
        int rowEffected = tagsService.deleteTags(postDto.getId());
        if (rowEffected > 0) {
            responseStatusDto.setStatus("Post and Tags Deleted");
        } else {
            responseStatusDto.setStatus("No post and tags found");
        }
        return new ResponseEntity(responseStatusDto, HttpStatus.OK);
    }

    @PostMapping("createComment")
    public ResponseEntity<ResponseStatusDto> createComment(@RequestBody CommentDto commentDto) {
        ResponseStatusDto responseStatusDto = new ResponseStatusDto();
        commentService.createComment(commentDto);
        responseStatusDto.setStatus("comment saved");

        return new ResponseEntity(responseStatusDto, HttpStatus.OK);
    }


    @GetMapping("getComment")
    public ResponseEntity<List<CommentDto>> getComment(@RequestBody CommentDto commentDto) {

        List<CommentDto> commentsDto = commentService.retrieveComments(commentDto.getPostId());

        return new ResponseEntity(commentsDto, HttpStatus.OK);
    }

    @PostMapping("updatecomment")
    public ResponseEntity<ResponseStatusDto> updateComment(@RequestBody CommentDto commentDto) {
        ResponseStatusDto responseStatusDto = new ResponseStatusDto();
        int rowEffected = commentService.updateComment(commentDto);
        if (rowEffected > 0) {
            responseStatusDto.setStatus("comment updated");
        } else {
            responseStatusDto.setStatus("no comment found");
        }
        return new ResponseEntity(responseStatusDto, HttpStatus.OK);
    }


    @PostMapping("deleteComment")
    public ResponseEntity<ResponseStatusDto> deleteComment(@RequestBody CommentDto commentDto) {
        ResponseStatusDto responseStatusDto = new ResponseStatusDto();
        int rowEffected = commentService.deleteComment(commentDto);
        if (rowEffected > 0) {
            responseStatusDto.setStatus("comment deleted");
        } else {
            responseStatusDto.setStatus("no comment found");
        }
        return new ResponseEntity(responseStatusDto, HttpStatus.OK);
    }


    @PostMapping("getTags")
    public ResponseEntity<List<List<Tag>>> getTags(@RequestBody TagDto tagDto) {
        return new ResponseEntity(tagsService.retrieveTags(tagDto.getPostId()), HttpStatus.OK);
    }

    @PostMapping("deleteTags")
    public ResponseEntity<ResponseStatusDto> deleteTags(@RequestBody TagDto tagDto) {
        ResponseStatusDto responseStatusDto = new ResponseStatusDto();

        int rowEffected = tagsService.deleteTags(tagDto.getPostId());

        if (rowEffected > 0) {
            responseStatusDto.setStatus("tags deleted");
        } else {
            responseStatusDto.setStatus("no tag found");
        }
        return new ResponseEntity(responseStatusDto, HttpStatus.OK);

    }

}


