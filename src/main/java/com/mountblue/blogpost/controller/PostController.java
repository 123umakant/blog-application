package com.mountblue.blogpost.controller;

import com.mountblue.blogpost.dto.PostDto;
import com.mountblue.blogpost.dto.RegisterDto;
import com.mountblue.blogpost.dto.ResponseStatusDto;
import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.service.AuthorService;
import com.mountblue.blogpost.service.PostService;
import com.mountblue.blogpost.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    AuthorService authorService;

    @Autowired
    TagsService tagsService;


    @PostMapping("/create")
    public ResponseEntity<ResponseStatusDto> post(@RequestBody PostDto postDto) {

        postService.savePost(postDto);
        ResponseStatusDto responseStatusDto = new ResponseStatusDto();
        responseStatusDto.setStatus("Post Saved");
        return new ResponseEntity(responseStatusDto, HttpStatus.OK);
    }
    @GetMapping("/get")
    public ResponseEntity<List<Post>> postPage(@RequestBody RegisterDto registerDto) {

        List<Post> posts = authorService.getPosts(registerDto.getEmail(), registerDto.getPassword());
        return new ResponseEntity(posts, HttpStatus.OK);
    }
    @PutMapping("/update")
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

    @DeleteMapping("delete")
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

}
