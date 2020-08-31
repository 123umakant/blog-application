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
public class PostRepositoryImpl {


    private final int LIMIT = 4;
    private final int OFFSET = 4;
    @PersistenceContext
    EntityManager entityManager;

    public Post insertPost(Post post) {
        return entityManager.merge(post);
    }

    public List<Post> findAllPost(int page) {
        return entityManager.createNativeQuery("SELECT * FROM Post orders limit 4 offset " + ((page - 1) * 4), Post.class).getResultList();
    }

    public List<Post> findAllPost(int page, String sort) {
        String query = "";

        if (sort.equals("new")) {
            query = "select * from post published_at order by published_at asc limit "
                    + LIMIT + " offset " + ((page - 1) * OFFSET);
        } else {
            query = "select * from post published_at order by published_at desc limit "
                    + LIMIT + " offset " + ((page - 1) * OFFSET);
        }
        return entityManager.createNativeQuery(query, Post.class).getResultList();
    }

    public List<Post> findAllPostValue(long postId) {

        return entityManager.createNativeQuery("select * from Post where id=" + postId, Post.class).getResultList();
    }


    public int updatePostData(Post post) {

        return entityManager.createNativeQuery("update post set author='" + post.getAuthor() + "',content='"
                + post.getContent() + "',excerpt='" + post.getExcerpt() + "',title='" + post.getTitle() + "',updated_at='" +
                post.getUpdatedAt() + "' where id=" + post.getId(),Post.class).executeUpdate();
    }

    public List<Post> getDataByAuthorName(String author) {
        String query = "select * from post where author='" + author + "'";
        return entityManager.createNativeQuery(query, Post.class).getResultList();
    }

    public List<Post> getDataByPublishDate(String publishDate) {
        String query = "select * from post where CAST(published_at as DATE)='" + publishDate + "'";
        return entityManager.createNativeQuery(query, Post.class).getResultList();
    }

    public List<Post> fetchSearchedPost(String search) {

        String query = "select * from post where " +
                "to_tsvector(author ||' ' || content || ' ' || excerpt || ' ' || title) @@ to_tsquery('" + search + "')";
        return entityManager.createNativeQuery(query, Post.class).getResultList();
    }

    public List<Post> getAllPost() {
        return entityManager.createNativeQuery("select * from post", Post.class).getResultList();

    }

    public void   deletePostData(String query) {

        entityManager.createNativeQuery(query,Post.class).executeUpdate();
    }


    public List<Object> getId() {
        return  entityManager.createNativeQuery("SELECT * FROM post ORDER BY id DESC LIMIT 1").getResultList();

    }

    public List<Post> findPosts(Integer page, String search, String sort, String publishDate) {
        if (sort.equals("new")) sort = "asc";
        else sort = "desc";

        String query = "select * from post where to_tsvector(author ||' ' || content || ' ' || excerpt || ' ' || title) @@ to_tsquery('" + search + "') and CAST(published_at as DATE)='" + publishDate + "'";


        return   entityManager.createNativeQuery(query).getResultList();
    }
}
