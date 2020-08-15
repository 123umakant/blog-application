package com.mountblue.blogpost.service;

import com.mountblue.blogpost.model.Tag;
import com.mountblue.blogpost.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TagsService {

    @Autowired
    TagsRepository tagsRepository;

    Tag tag = new Tag();
    public void saveTags(){
        Date date =new Date();
        tag.setId(1);
        tag.setName("Java");
        tag.setCreatedAt(date);
        tag.setUpdatedAt(date);
        tagsRepository.insertTags(tag);
    }
    public List<Tag> retriveTags(){
     return tagsRepository.retriveAllTags();
    }
}
