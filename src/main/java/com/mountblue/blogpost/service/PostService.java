package com.mountblue.blogpost.service;

import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    AuthorService authorService;
    Post post = new Post();


    public void savePost(Post post) {
        Date date = new Date();
        post.setCreatedAt(date);
        post.setPublished(false);
        post.setUpdatedAt(date);
        post.setPublishedAt(date);
        postRepository.insertPost(post);

    }

    public List<Post> retireAllPostValues(int page) {
        return postRepository.findAllPost(page);
    }

    public List<Post> retireAllPostValues(int page, String sort) {
        String query = "";
        if (sort == "new") {
            query = "select * from post published_at order by published_at asc limit 4 offset " + ((page - 1) * 4);
        } else {
            query = "select * from post published_at order by published_at desc limit 4 offset " + ((page - 1) * 4);
        }
        return postRepository.findAllPost(page, query);


    }

    public List<Post> retirePostValues(String id) {
        return postRepository.findAllPostValue(Long.parseLong(id));
    }

    public void updatePost(Post post) {
        postRepository.updatePostData(post);
    }

    public List<Post> fetchDataByAuthorName(String author) {

        String query = "select * from post where author='" + author + "'";

        return postRepository.getDataByAuthorName(query);
    }

    public List<Post> fetchDataByPublishDate(String publishDate) {

        String query = "select * from post where CAST(published_at as DATE)='" + publishDate + "'";

        return postRepository.getDataByPublishDate(query);

    }

    public List<Post> getSearchedPost(String search) {

        String query = "select * from post where to_tsvector(author ||' ' || content || ' ' || excerpt || ' ' || title) @@ to_tsquery('" + search + "')";
        return postRepository.fetchSearchedPost(query);
    }

    public List<Post> retireAllPost() {
        String query = "select * from post";
        return postRepository.getAllPost(query);
    }

    public void deletePost(String postId) {
        long id =Long.parseLong(postId);
        String query ="delete from post where id="+id;
        postRepository.deletePostData(query);
    }
}
