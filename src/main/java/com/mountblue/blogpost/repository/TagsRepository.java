package com.mountblue.blogpost.repository;


import com.mountblue.blogpost.model.PostTag;
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

    public List<PostTag> retriveAllTags(String query) {
        return entityManager.createNativeQuery(query, PostTag.class).getResultList();

    }

    public void saveTagsData(Tag tag) {
         entityManager.merge(tag);
    }


    public List<Object> getId() {
       return entityManager.createNativeQuery("SELECT * FROM tag ORDER BY id DESC LIMIT 1;").getResultList();

    }

    public List<Tag> getTags(String queryTag) {
        return entityManager.createNativeQuery(queryTag, Tag.class).getResultList();
    }

    public List<Tag> getTags() {

        return entityManager.createNativeQuery("select distinct * from Tag limit 4",Tag.class).getResultList();
    }

    public int deleteTags(String queryTag) {

        return entityManager.createNativeQuery(queryTag,Tag.class).executeUpdate();
    }
}
