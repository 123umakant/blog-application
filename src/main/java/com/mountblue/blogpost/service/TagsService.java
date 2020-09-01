package com.mountblue.blogpost.service;

import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.model.PostTag;
import com.mountblue.blogpost.model.Tag;
import com.mountblue.blogpost.repository.PostRepositoryImpl;
import com.mountblue.blogpost.repository.PostTagRepository;
import com.mountblue.blogpost.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class TagsService {

    private final int TAG_ID=0;

    private final int POST_ID_INDEX=0;

    @Autowired
    TagsRepository tagsRepository;

    @Autowired
    PostTagRepository postTagRepository;
    @Autowired
    PostRepositoryImpl postRepositoryImpl;

    Tag tag = new Tag();

    public void saveTags(String tags, Long postId) {
        String[] stringArray = tags.split(",");

        for (String tagValue : stringArray) {
            long id = 0;

            Tag tag = new Tag();
            PostTag postTag = new PostTag();
            tag.setName(tagValue);
            tag.setCreatedAt(new Date());
            tag.setUpdatedAt(new Date());
            tagsRepository.saveTagsData(tag);
            List<Object> tagId = tagsRepository.getId();

            Iterator itr = tagId.iterator();
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                BigInteger intId = (BigInteger) obj[TAG_ID];
                id = intId.longValue();
            }

            postTag.setPostId(postId);
            postTag.setTagId(id);
            postTag.setCreatedAt(new Date());
            postTag.setUpdatedAt(new Date());
            postTagRepository.savePostTag(postTag);
        }
    }

    public List<List<Tag>> retrieveTags(long postId) {

        List<List<Tag>> tagsArray = new ArrayList<>();
        List<PostTag> postTags = tagsRepository.retrieveAllTags(postId);
        Iterator<PostTag> itr = postTags.iterator();
        while (itr.hasNext()) {
            PostTag postTag = itr.next();
            List<Tag> tags = tagsRepository.getTags(postTag);
            tagsArray.add(tags);
        }
        return tagsArray;
    }

    public List<Tag> retireAllValues() {
        return tagsRepository.getTags();
    }

    public List<Post> retireAllPostValues(String tagSearchId) {

        List<PostTag> postTags = postTagRepository.getPostsId(tagSearchId);
        return  postRepositoryImpl.findAllPostValue(postTags.get(POST_ID_INDEX).getPostId());
    }

    public int deleteTags(long id) {
        List<PostTag> postTags = tagsRepository.retrieveAllTags(id);

        Iterator<PostTag> itr = postTags.iterator();
        while (itr.hasNext()) {
            PostTag postTag = itr.next();
           long tagId = postTag.getTagId();
            tagsRepository.deleteTags(tagId);
        }
        return postTagRepository.deletePostTags(id);
    }
}