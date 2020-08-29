package com.mountblue.blogpost.repository;


import com.mountblue.blogpost.model.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Author insert(Author user) {
        return entityManager.merge(user);
    }

    public List<Author> findAll() {
        return entityManager.createQuery("SELECT e FROM Visitor e", Author.class).getResultList();
    }

}
