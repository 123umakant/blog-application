package com.mountblue.blogpost.repository;


import com.mountblue.blogpost.model.Visitor;
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

    public Visitor insert(Visitor user) {
        return entityManager.merge(user);
    }

    public List<Visitor> findAll() {
        return entityManager.createQuery("SELECT e FROM Visitor e", Visitor.class).getResultList();
    }

}
