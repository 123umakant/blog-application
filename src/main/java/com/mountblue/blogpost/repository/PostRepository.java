package com.mountblue.blogpost.repository;

import com.mountblue.blogpost.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}
