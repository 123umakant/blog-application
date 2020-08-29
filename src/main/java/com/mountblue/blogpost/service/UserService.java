package com.mountblue.blogpost.service;

import com.mountblue.blogpost.model.Author;
import com.mountblue.blogpost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    Author author = new Author();

    public void saveUserdetail() {
        author.setId(1L);
        author.setName("umakant");
        author.setEmail("umakant.un@gmail.com");
        author.setPassword("root");
        System.out.println(repository.insert(author));
    }

    public List<Author> retireAllValues() {
        return repository.findAll();
    }
}
