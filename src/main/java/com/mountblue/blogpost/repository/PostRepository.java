package com.mountblue.blogpost.repository;

import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.model.Visitor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PostRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Post insertPost(Post post) {
        return entityManager.merge(post);
    }

    public List<Post> findAllPost() {
        return entityManager.createQuery("SELECT e FROM Post e", Post.class).getResultList();
    }

    public List<Post> findAllPostValue(long id) {

        return entityManager.createNativeQuery("select * from Post where id=" + id, Post.class).getResultList();
    }


    public void updatePostData(Post post) {
         entityManager.createNativeQuery("update post set author='" + post.getAuthor() + "',content='"
                + post.getContent() + "',excerpt='"+post.getExcerpt()+"',title='"+post.getTitle()+"',updated_at='"+
                post.getUpdatedAt()+"' where id='"+post.getId()+"'").executeUpdate();
    }
}
