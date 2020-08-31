package com.mountblue.blogpost.service;

import com.mountblue.blogpost.dto.PostDto;
import com.mountblue.blogpost.model.Author;
import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.repository.AuthorRepository;
import com.mountblue.blogpost.repository.PostRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final int POST_ID=0;

    @Autowired
    PostService postService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    PostRepositoryImpl postRepositoryImpl;


    @Autowired
    TagsService tagsService;

    Post post = new Post();

    public void savePost(PostDto postDto) {


        Post post = new Post();
        Author author = new Author();

        User loggedInUser = authorService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        author.setName(loggedInUser.getUsername());
        Optional<Author> authorId = authorRepository.findByName(author.getName());
        Date date = new Date();
        post.setTitle(postDto.getTitle());
        post.setExcerpt(postDto.getExcerpt());
        post.setContent(postDto.getContent());
        post.setAuthor(postDto.getAuthor());
        post.setCreatedAt(date);
        post.setPublished(false);
        post.setUpdatedAt(date);
        post.setPublishedAt(date);
        post.setAuthor_id(authorId.get().getId());
        postRepositoryImpl.insertPost(post);
        tagsService.saveTags(postDto.getTags(), postService.getId());

    }

    public List<Post> retireAllPostValues(int page) {
        return postRepositoryImpl.findAllPost(page);
    }

    public List<Post> retireAllPostValues(int page, String sort) {

        return postRepositoryImpl.findAllPost(page, sort);
    }

    public List<Post> retirePostValues(String id) {
        return postRepositoryImpl.findAllPostValue(Long.parseLong(id));
    }

    public int updatePost(PostDto postDto) {

        Post post = new Post();
        Author author = new Author();

        User loggedInUser = authorService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        author.setName(loggedInUser.getUsername());
        Optional<Author> authorId = authorRepository.findByName(author.getName());
        if (!authorId.get().getRole().equals("admin")){

            post.setAuthor_id(authorId.get().getId());
        }

        Date date = new Date();
        post.setTitle(postDto.getTitle());
        post.setExcerpt(postDto.getExcerpt());
        post.setContent(postDto.getContent());
        post.setAuthor(postDto.getAuthor());
        post.setPublished(false);
        post.setUpdatedAt(date);
        post.setPublishedAt(date);

       return postRepositoryImpl.updatePostData(post);

    }

    public List<Post> fetchDataByAuthorName(String author) {

        return postRepositoryImpl.getDataByAuthorName(author);
    }

    public List<Post> fetchDataByPublishDate(String publishDate) {


        return postRepositoryImpl.getDataByPublishDate(publishDate);
    }

    public List<Post> getSearchedPost(String search) {

        return postRepositoryImpl.fetchSearchedPost(search);
    }

    public List<Post> retireAllPost() {
        return postRepositoryImpl.getAllPost();
    }

    public void deletePost(long postId) {
        Author author = new Author();
        String query=null;
        User loggedInUser = authorService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        author.setName(loggedInUser.getUsername());
        Optional<Author> authorId = authorRepository.findByName(author.getName());
            if(authorId.get().getRole().equals("admin")){
                 query = "delete from post where id=" + postId;
            }else
            {
                query = "delete from post where id=" + postId+" and author_id="+authorId.get().getId();
            }
        postRepositoryImpl.deletePostData(query);
    }

    public long getId() {
      long id=0;
        List<Object> postId = postRepositoryImpl.getId();
        Iterator itr = postId.iterator();
        while (itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            BigInteger intId = (BigInteger) obj[POST_ID];
            id = intId.longValue();
        }
        return id;
    }

    public List<Post> getSearchedPostAll(Integer page, String search, String sort, String publishDate) {

        return postRepositoryImpl.findPosts(page,search,sort,publishDate);
    }
}
