package com.mountblue.blogpost.service;

import com.mountblue.blogpost.dto.PostDto;
import com.mountblue.blogpost.exception.PostNotFoundException;
import com.mountblue.blogpost.model.Author;
import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.repository.AuthorRepositoryNew;
import com.mountblue.blogpost.repository.PostRepositoryNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Date;

import static java.util.stream.Collectors.toList;

@Service
public class PostServiceNew {

    @Autowired
    private AuthServiceOld authServiceOld;
    @Autowired
    private PostRepositoryNew postRepositoryNew;

    @Autowired
    private AuthorRepositoryNew authorRepositoryNew;

    @Transactional
    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepositoryNew.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }
    @Transactional
    public void createPost(PostDto postDto) {
        //Post post = mapFromDtoToPost(postDto);
        Post post = new Post();
        Author author = new Author();

        User loggedInUser = authServiceOld.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        author.setName(loggedInUser.getUsername());
        Optional<Author> authorId = authorRepositoryNew.findByName(author.getName());
        Date date = new Date();
        post.setTitle(postDto.getTitle());
        post.setExcerpt(postDto.getExcerpt());
        postDto.setContent(postDto.getContent());
        postDto.setAuthor(postDto.getAuthor());
        post.setCreatedAt(date);
        post.setPublished(false);
        post.setUpdatedAt(date);
        post.setPublishedAt(date);
        post.setAuthor_id(authorId.get().getId());
        //postRepository.insertPost(post);

        postRepositoryNew.save(post);
    }
    @Transactional
    public PostDto readSinglePost(Long id) {
        Post post = postRepositoryNew.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setAuthor(post.getAuthor());
        return postDto;
    }
    private Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User loggedInUser = authServiceOld.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setCreatedAt(new Date());
        post.setAuthor(loggedInUser.getUsername());
        post.setUpdatedAt(new Date());
        return post;
    }

}
