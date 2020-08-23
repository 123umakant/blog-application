package com.mountblue.blogpost.repository;

import com.mountblue.blogpost.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<Visitor,Long> {
    Visitor findByName(String userName);
}
