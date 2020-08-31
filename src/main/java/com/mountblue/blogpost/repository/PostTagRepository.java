package com.mountblue.blogpost.repository;

import com.mountblue.blogpost.model.PostTag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PostTagRepository {

    @PersistenceContext
    EntityManager entityManager;


    public void savePostTag(PostTag postTag) {

        entityManager.merge(postTag);
    }

    public List<Object> getPostsId(String tagSearchId) {
        String query = "select * from post_tag where tag_id=" + tagSearchId;

       return entityManager.createNativeQuery(query).getResultList();
    }

    public int deletePostTags(long id) {
        String queryPostTags = "delete from post_tag where post_id=" + id;
       return entityManager.createNativeQuery(queryPostTags,PostTag.class).executeUpdate();

    }
}
