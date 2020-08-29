package com.mountblue.blogpost.service;

import com.mountblue.blogpost.dto.PostDto;
import com.mountblue.blogpost.model.Author;
import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.repository.AuthorRepositoryNew;
import com.mountblue.blogpost.repository.PostRepository;
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

    private final int LIMIT = 4;
    private final int OFFSET = 4;

    @Autowired
    PostService postService;

    @Autowired
    private AuthServiceOld authServiceOld;

    @Autowired
    private AuthorRepositoryNew authorRepositoryNew;

    @Autowired
    PostRepository postRepository;

    @Autowired
    AuthorService authorService;

    @Autowired
    TagsService tagsService;

    Post post = new Post();

    public void savePost(PostDto postDto) {

        Post post = new Post();
        Author author = new Author();

        User loggedInUser = authServiceOld.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        author.setName(loggedInUser.getUsername());
        Optional<Author> authorId = authorRepositoryNew.findByName(author.getName());
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
        postRepository.insertPost(post);
        tagsService.saveTags(postDto.getTags(), postService.getId());

    }

    public List<Post> retireAllPostValues(int page) {
        return postRepository.findAllPost(page);
    }

    public List<Post> retireAllPostValues(int page, String sort) {
        String query = "";

        if (sort.equals("new")) {
            query = "select * from post published_at order by published_at asc limit " +
                    LIMIT + " offset " + ((page - 1) * OFFSET);
        } else {
            query = "select * from post published_at order by published_at desc limit "
                    + LIMIT + " offset " + ((page - 1) * OFFSET);
        }
        return postRepository.findAllPost(page, query);
    }

    public List<Post> retirePostValues(String id) {
        return postRepository.findAllPostValue(Long.parseLong(id));
    }

    public int updatePost(PostDto postDto) {

        Post post = new Post();
        Author author = new Author();

        User loggedInUser = authServiceOld.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        author.setName(loggedInUser.getUsername());
        Optional<Author> authorId = authorRepositoryNew.findByName(author.getName());

        Date date = new Date();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setExcerpt(postDto.getExcerpt());
        post.setContent(postDto.getContent());
        post.setAuthor(postDto.getAuthor());
        post.setPublished(false);
        post.setUpdatedAt(date);
        post.setPublishedAt(date);
        post.setAuthor_id(authorId.get().getId());
       return postRepository.updatePostData(post);

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

        String query = "select * from post where " +
                "to_tsvector(author ||' ' || content || ' ' || excerpt || ' ' || title) @@ to_tsquery('" + search + "')";
        return postRepository.fetchSearchedPost(query);
    }

    public List<Post> retireAllPost() {
        String query = "select * from post";
        return postRepository.getAllPost(query);
    }

    public void deletePost(long postId) {
        Author author = new Author();

        User loggedInUser = authServiceOld.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        author.setName(loggedInUser.getUsername());
        Optional<Author> authorId = authorRepositoryNew.findByName(author.getName());

        String query = "delete from post where id=" + postId+" and author_id="+authorId.get().getId();
        postRepository.deletePostData(query);
    }

    public long getId() {
        long id = 0;
        List<Object> postId = postRepository.getId();
        Iterator itr = postId.iterator();
        while (itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            BigInteger intId = (BigInteger) obj[0];
            id = intId.longValue();
        }
        return id;
    }

    public List<Post> getSearchedPostAll(Integer page, String search, String sort, String publishDate) {
        if (sort.equals("new")) sort = "asc";
        else sort = "desc";

        String query = "select * from post where to_tsvector(author ||' ' || content || ' ' || excerpt || ' ' || title) @@ to_tsquery('" + search + "') and CAST(published_at as DATE)='" + publishDate + "'";

        return postRepository.findPosts(query);
    }
}
