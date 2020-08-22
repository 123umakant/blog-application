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
public class AuthorRepository {

    @PersistenceContext
    EntityManager entityManager;

    public void saveLoginDetail(Visitor visitor) {

        List<Visitor> list = entityManager.createNativeQuery("select * from visitor where email='" + visitor.getEmail() + "' and password='" + visitor.getPassword() + "'",
                Visitor.class).getResultList();

        if (list.size() == 0) {
            entityManager.merge(visitor);
        }
    }

    public boolean verifyDetail(Visitor visitor) {
        boolean flag = false;
        List<Visitor> list = entityManager.createNativeQuery("select * from visitor where email='" + visitor.getEmail() + "' and password='" + visitor.getPassword() + "'",
                Visitor.class).getResultList();

        if (list.size() == 1) {
            flag = true;
        }
        return flag;

    }


    public List<Post> getAuthorPosts(long visitor) {

        return entityManager.createNativeQuery("select * from post where visitor_id='" + visitor + "'"
                , Post.class).getResultList();
    }

    public List<Visitor> findId(String userName, String password) {

        return entityManager.createNativeQuery("select * from visitor where email='" +
                        userName + "' and password='" + password + "'",
                Visitor.class).getResultList();

    }

    public boolean verifyAdminDetail(String query) {
        boolean flag = false;
        List<Visitor> list = entityManager.createNativeQuery(query, Visitor.class).getResultList();

        if (list.size() == 1) {
            flag = true;
        }

        return flag;
    }
}
