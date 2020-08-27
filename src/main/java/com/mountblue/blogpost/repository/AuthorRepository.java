package com.mountblue.blogpost.repository;

import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.model.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AuthorRepository {

    @PersistenceContext
    EntityManager entityManager;

    public void saveLoginDetail(Author author) {

        List<Author> list = entityManager.createNativeQuery("select * from visitor where email='" + author.getEmail() + "' and password='" + author.getPassword() + "'",
                Author.class).getResultList();

        if (list.size() == 0) {
            entityManager.merge(author);
        }
    }

    public boolean verifyDetail(Author author) {
        boolean flag = false;
        List<Author> list = entityManager.createNativeQuery("select * from visitor where email='" + author.getEmail() + "' and password='" + author.getPassword() + "'",
                Author.class).getResultList();

        if (list.size() == 1) {
            flag = true;
        }
        return flag;

    }


    public List<Post> getAuthorPosts(long visitor) {

        return entityManager.createNativeQuery("select * from post where visitor_id='" + visitor + "'"
                , Post.class).getResultList();
    }

    public List<Author> findId(String userName, String password) {

        return entityManager.createNativeQuery("select * from visitor where email='" +
                        userName + "' and password='" + password + "'",
                Author.class).getResultList();

    }

    public boolean verifyAdminDetail(String query) {
        boolean flag = false;
        List<Author> list = entityManager.createNativeQuery(query, Author.class).getResultList();

        if (list.size() == 1) {
            flag = true;
        }

        return flag;
    }
}
