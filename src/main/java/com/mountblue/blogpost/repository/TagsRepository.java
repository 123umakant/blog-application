package com.mountblue.blogpost.repository;


import com.mountblue.blogpost.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TagsRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Tag insertTags(Tag tag) {
        return entityManager.merge(tag);
    }

    public List<Tag> retriveAllTags() {
        return entityManager.createQuery("SELECT e FROM Tag e", Tag.class).getResultList();
    }
}
