package com.mountblue.blogpost.repository;

import com.mountblue.blogpost.model.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
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

    public List<Post> findAllPost(int page) {
        return entityManager.createNativeQuery("SELECT * FROM Post orders limit 4 offset " + ((page - 1) * 4), Post.class).getResultList();
    }

    public List<Post> findAllPost(int page, String query) {
        return entityManager.createNativeQuery(query, Post.class).getResultList();
    }

    public List<Post> findAllPostValue(long id) {

        return entityManager.createNativeQuery("select * from Post where id=" + id, Post.class).getResultList();
    }


    public int updatePostData(Post post) {
        return entityManager.createNativeQuery("update post set author='" + post.getAuthor() + "',content='"
                + post.getContent() + "',excerpt='" + post.getExcerpt() + "',title='" + post.getTitle() + "',updated_at='" +
                post.getUpdatedAt() + "' where id=" + post.getId(),Post.class).executeUpdate();
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


    public List<Object> getId() {
        return  entityManager.createNativeQuery("SELECT * FROM post ORDER BY id DESC LIMIT 1").getResultList();

    }

    public List<Post> findPosts(String query) {

      return   entityManager.createNativeQuery(query).getResultList();
    }
}
