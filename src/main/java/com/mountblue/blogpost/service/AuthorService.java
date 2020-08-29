package com.mountblue.blogpost.service;

import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.model.Author;
import com.mountblue.blogpost.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final int VISITOR_INDEX = 0;
    @Autowired
    AuthorRepository authorRepository;




    public boolean verifyDetail(Author author) {
        boolean value = authorRepository.verifyDetail(author);
        return value;
    }

    public void saveLoginDetail(Author author) {
        authorRepository.saveLoginDetail(author);
    }


    public boolean verifyAdminDetail(Author author) {
        String query = "select * from visitor where name='" + author.getName() + "' and password='" +
                author.getPassword() + "'";
        return authorRepository.verifyAdminDetail(query);
    }

    public List<Post> getPosts(String email, String password) {
        List<Author> author = authorRepository.findId(email, password);
         long authorId = author.get(VISITOR_INDEX).getId();
       return authorRepository.getAuthorPosts(authorId);
    }
}
