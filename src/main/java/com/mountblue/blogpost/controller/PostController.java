package com.mountblue.blogpost.controller;

import com.mountblue.blogpost.dto.PostDto;
import com.mountblue.blogpost.service.PostServiceNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")

public class PostController {
    @Autowired
    private PostServiceNew postServiceNew;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostDto postDto) {
        postServiceNew.createPost(postDto);
        return new ResponseEntity<>("Registration Successful", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> showAllPosts() {
        return new ResponseEntity<>(postServiceNew.showAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(postServiceNew.readSinglePost(id), HttpStatus.OK);

    }
}
