package com.mountblue.blogpost.service;

import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.model.Visitor;
import com.mountblue.blogpost.repository.PostRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
            AuthorService authorService;
    Post post = new Post();



    public void savePost(Post post) {
        Date date=new Date();
        post.setCreatedAt(date);
        post.setPublished(false);
        post.setUpdatedAt(date);
        post.setPublishedAt(date);
        postRepository.insertPost(post);

    }
    public List<Post> retireAllPostValues(){
        return  postRepository.findAllPost();
    }
    public List<Post> retirePostValues(String id){
        return  postRepository.findAllPostValue(Long.parseLong(id));
    }

}
