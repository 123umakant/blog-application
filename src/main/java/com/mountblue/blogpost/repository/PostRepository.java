package com.mountblue.blogpost.repository;

import com.mountblue.blogpost.model.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public List<Post> findAllPost(int page) {
        return entityManager.createNativeQuery("SELECT * FROM Post orders limit 4 offset " + ((page - 1) * 4), Post.class).getResultList();
    }

    public List<Post> findAllPost(int page, String query) {
        return entityManager.createNativeQuery(query, Post.class).getResultList();
    }

    public List<Post> findAllPostValue(long id) {

        return entityManager.createNativeQuery("select * from Post where id=" + id, Post.class).getResultList();
    }


    public void updatePostData(Post post) {
        entityManager.createNativeQuery("update post set author='" + post.getAuthor() + "',content='"
                + post.getContent() + "',excerpt='" + post.getExcerpt() + "',title='" + post.getTitle() + "',updated_at='" +
                post.getUpdatedAt() + "' where id='" + post.getId() + "'").executeUpdate();
    }

    public List<Post> getDataByAuthorName(String query) {

        return entityManager.createNativeQuery(query, Post.class).getResultList();
    }

    public List<Post> getDataByPublishDate(String query) {
        return entityManager.createNativeQuery(query, Post.class).getResultList();
    }

    public List<Post> fetchSearchedPost(String query) {

        return entityManager.createNativeQuery(query, Post.class).getResultList();
    }

    public List<Post> getAllPost(String query) {
        return entityManager.createNativeQuery(query, Post.class).getResultList();

    }

    public void   deletePostData(String query) {
        entityManager.createNativeQuery(query,Post.class).executeUpdate();
    }
}
