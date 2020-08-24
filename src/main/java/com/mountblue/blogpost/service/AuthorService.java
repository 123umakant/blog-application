package com.mountblue.blogpost.service;

import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.model.Visitor;
import com.mountblue.blogpost.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final int VISITOR_INDEX = 0;
    @Autowired
    AuthorRepository authorRepository;

    public boolean verifyDetail(Visitor visitor) {
        boolean value = authorRepository.verifyDetail(visitor);
        return value;
    }

    public void saveLoginDetail(Visitor visitor) {
        authorRepository.saveLoginDetail(visitor);
    }

    public List<Post> getAuthorPosts(long id) {
        return authorRepository.getAuthorPosts(id);
    }

    public long getId(String userName, String password) {
        List<Visitor> visitor = authorRepository.findId(userName, password);
        return visitor.get(VISITOR_INDEX).getId();
    }

    public boolean verifyAdminDetail(Visitor author) {
        String query = "select * from visitor where name='" + author.getName() + "' and password='" +
                author.getPassword() + "'";
        return authorRepository.verifyAdminDetail(query);
    }
}
