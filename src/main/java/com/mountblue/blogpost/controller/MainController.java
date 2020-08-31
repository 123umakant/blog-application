package com.mountblue.blogpost.controller;


import com.mountblue.blogpost.dto.FilterAndSearchPostDto;
import com.mountblue.blogpost.dto.SearchAndSortFields;
import com.mountblue.blogpost.service.PostService;
import com.mountblue.blogpost.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class MainController {

    @Autowired
    PostService postService;

    @Autowired
    TagsService tagsService;

    @GetMapping("")
    public ResponseEntity<FilterAndSearchPostDto> homePage(@RequestBody SearchAndSortFields searchAndSortFields) {

        FilterAndSearchPostDto filterAndSearchPostDto = new FilterAndSearchPostDto();

        HashMap hashMap = new HashMap();

        if (searchAndSortFields.getPage() < 1) {
            searchAndSortFields.setPage(1);
        }
        if (searchAndSortFields.getSearch() != null && searchAndSortFields.getSearch() != "" &&
                searchAndSortFields.getSort() != null && !searchAndSortFields.getSort().equals("Sort") &&
                searchAndSortFields.getPublishDate() != null) {

            hashMap.put("post", postService.getSearchedPostAll(searchAndSortFields.getPage(),
                    searchAndSortFields.getSearch(), searchAndSortFields.getSort(), searchAndSortFields.getPublishDate()));
        }
        if (searchAndSortFields.getSearch() != null && searchAndSortFields.getSearch() != "") {

            hashMap.put("post", postService.getSearchedPost(searchAndSortFields.getSearch()));

        } else if (searchAndSortFields.getSort() != null && !searchAndSortFields.getSort().equals("Sort")) {

            hashMap.put("post", postService.retireAllPostValues(searchAndSortFields.getPage(),
                    searchAndSortFields.getSort()));

        } else if (searchAndSortFields.getPublishDate() != null) {

            hashMap.put("post", postService.fetchDataByPublishDate(searchAndSortFields.getPublishDate()));

        } else if (searchAndSortFields.getTagSearch() != null) {

            hashMap.put("post", tagsService.retireAllPostValues(searchAndSortFields.getTagSearchId()));

        } else {

            hashMap.put("post", postService.retireAllPostValues(searchAndSortFields.getPage()));

        }

        hashMap.put("tags", tagsService.retireAllValues());
        hashMap.put("page", searchAndSortFields.getPage());

        filterAndSearchPostDto.setHashMap(hashMap);
        return new ResponseEntity(filterAndSearchPostDto, HttpStatus.OK);
    }
}


