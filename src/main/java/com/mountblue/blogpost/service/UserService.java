package com.mountblue.blogpost.service;

import com.mountblue.blogpost.model.Visitor;
import com.mountblue.blogpost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    Visitor visitor = new Visitor();

    public void saveUserdetail() {
        visitor.setId(1L);
        visitor.setName("umakant");
        visitor.setEmail("umakant.un@gmail.com");
        visitor.setPassword("root");
        System.out.println(repository.insert(visitor));
    }

    public List<Visitor> retireAllValues() {
        return repository.findAll();
    }
}
