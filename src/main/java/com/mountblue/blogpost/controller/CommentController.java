package com.mountblue.blogpost.controller;

import com.mountblue.blogpost.dto.CommentDto;
import com.mountblue.blogpost.dto.ResponseStatusDto;
import com.mountblue.blogpost.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<ResponseStatusDto> createComment(@RequestBody CommentDto commentDto) {
        ResponseStatusDto responseStatusDto = new ResponseStatusDto();
        commentService.createComment(commentDto);
        responseStatusDto.setStatus("comment saved");

        return new ResponseEntity(responseStatusDto, HttpStatus.OK);
    }


    @GetMapping("/get")
    public ResponseEntity<List<CommentDto>> getComment(@RequestBody CommentDto commentDto) {

        List<CommentDto> commentsDto = commentService.retrieveComments(commentDto.getPostId());

        return new ResponseEntity(commentsDto, HttpStatus.OK);
    }

    @PutMapping("/update")
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


    @DeleteMapping("/delete")
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

}
