package com.mountblue.blogpost.repository;

import com.mountblue.blogpost.model.PostTag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class PostTagRepository {

    @PersistenceContext
    EntityManager entityManager;


    public void savePostTag(PostTag postTag) {

        entityManager.merge(postTag);
    }
}
