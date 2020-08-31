package com.mountblue.blogpost.controller;

import com.mountblue.blogpost.dto.ResponseStatusDto;
import com.mountblue.blogpost.dto.TagDto;
import com.mountblue.blogpost.model.Tag;
import com.mountblue.blogpost.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagsService tagsService;

    @GetMapping("/get")
    public ResponseEntity<List<List<Tag>>> getTags(@RequestBody TagDto tagDto) {
        return new ResponseEntity(tagsService.retrieveTags(tagDto.getPostId()), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
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
