package com.mountblue.blogpost.repository;

import com.mountblue.blogpost.dto.CommentDto;
import com.mountblue.blogpost.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class CommentRepository {

    @PersistenceContext
    EntityManager entityManager;

    public void saveCommentData(Comment comment) {
        entityManager.merge(comment);
    }

    public List<Comment> retriveComments(long postId) {

        return entityManager.createNativeQuery("select * from Comment where post_id=" + postId,Comment.class).getResultList();
    }

    public int deleteComment(CommentDto commentDto) {
        String query = "delete from Comment where post_id=" + commentDto.getPostId();
         return entityManager.createNativeQuery(query,Comment.class).executeUpdate();
    }


    public List<Comment> getComments(String postId, String commentId) {
        return entityManager.createNativeQuery("select * from Comment where id=" + Long.parseLong(commentId)).getResultList();
    }

    public int updateComment(Comment postComment) {
        String query ="update Comment set comment='" + postComment.getComment()+
                "',updated_at='"+postComment.getUpdatedAt()+"'where post_id='"+postComment.getPostId()+"'";
       return entityManager.createNativeQuery(query,Comment.class).executeUpdate();
    }
}
