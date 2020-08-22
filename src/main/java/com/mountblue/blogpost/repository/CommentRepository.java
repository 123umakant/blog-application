package com.mountblue.blogpost.repository;

import com.mountblue.blogpost.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CommentRepository {

    @PersistenceContext
    EntityManager entityManager;

    public void saveCommentData(Comment comment) {
        entityManager.merge(comment);
    }

    public List<Comment> retriveComments(String postId) {

        return entityManager.createNativeQuery("select * from Comment where post_id=" + Long.parseLong(postId)).getResultList();
    }

    public void deleteCommentData(String query) {
        System.out.println(entityManager.createNativeQuery(query,Comment.class).executeUpdate());
    }
}
