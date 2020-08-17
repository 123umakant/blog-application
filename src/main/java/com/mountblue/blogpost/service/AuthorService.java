package com.mountblue.blogpost.service;

import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.model.Visitor;
import com.mountblue.blogpost.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public boolean verifyDetail(Visitor visitor) {

        boolean value = authorRepository.verifyDetail(visitor);
        return value;
    }


    public void saveLoginDetail(Visitor visitor) {
       authorRepository.saveLoginDetail(visitor);
    }

    public List<Post> getAuthorPosts(long id){
          return authorRepository.getAuthorPosts(id);
    }
    public long getId(String userName,String password){

        List<Visitor> visitor = authorRepository.findId(userName,password);

        return visitor.get(0).getId();
    }
}
