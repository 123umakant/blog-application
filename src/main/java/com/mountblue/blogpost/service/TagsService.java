package com.mountblue.blogpost.service;

import com.mountblue.blogpost.model.PostTag;
import com.mountblue.blogpost.model.Tag;
import com.mountblue.blogpost.repository.PostTagRepository;
import com.mountblue.blogpost.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class TagsService {

    @Autowired
    TagsRepository tagsRepository;

    @Autowired
    PostTagRepository postTagRepository;

    Tag tag = new Tag();

    public void saveTags(String tags,Long postId) {
        String[] stringArray = tags.split(",");


        for (String tagValue:stringArray) {
            Tag tag =new Tag();
            PostTag postTag = new PostTag();

            tag.setName(tagValue);
            tag.setCreatedAt(new Date());
            tag.setUpdatedAt(new Date());
                long id=0;
            tagsRepository.saveTagsData(tag);
            List<Object> tagId=tagsRepository.getId();

            Iterator itr = tagId.iterator();
            while(itr.hasNext()){
                Object[] obj = (Object[]) itr.next();
                BigInteger intId = (BigInteger) obj[0];

                id = intId.longValue();
                System.out.println(id);
            }

            postTag.setPostId(postId);
            postTag.setTagId(id);
            postTag.setCreatedAt(new Date());
            postTag.setUpdatedAt(new Date());

            postTagRepository.savePostTag(postTag);

        }

    }

    public List<Tag> retriveTags() {
        return tagsRepository.retriveAllTags();
    }
}
