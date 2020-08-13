package com.mountblue.blogpost.service;

import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.model.Visitor;
import com.mountblue.blogpost.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    Post post = new Post();

    public void savePost() {
        Date date=new Date();
        post.setId(1);
        post.setAuthor("umakant");
        post.setContent("blog on thymeleaf");
        post.setCreatedAt(date);
        post.setExcerpt("In this article you will learn thymeleaf");
        post.setPublished(true);
        post.setTitle("Thymeleaf basic concepts");
        post.setUpdatedAt(date);
        post.setPublishedAt(date);
        postRepository.insertPost(post);
    }
    public List<Post> retireAllPostValues(){
        return  postRepository.findAllPost();
    }
}
