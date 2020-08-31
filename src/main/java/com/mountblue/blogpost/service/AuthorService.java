package com.mountblue.blogpost.service;

import com.mountblue.blogpost.dto.LoginDto;
import com.mountblue.blogpost.dto.RegisterDto;
import com.mountblue.blogpost.model.Author;
import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.repository.AuthorRepository;
import com.mountblue.blogpost.repository.AuthorRepositoryImpl;
import com.mountblue.blogpost.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final int VISITOR_INDEX = 0;
    @Autowired
    AuthorRepositoryImpl authorRepositoryImpl;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    public boolean verifyDetail(Author author) {
        boolean value = authorRepositoryImpl.verifyDetail(author);
        return value;
    }

    public void saveLoginDetail(Author author) {
        authorRepositoryImpl.saveLoginDetail(author);
    }

    public boolean verifyAdminDetail(Author author) {

        return authorRepositoryImpl.verifyAdminDetail(author);
    }

    public List<Post> getPosts(String email, String password) {
        List<Author> author = authorRepositoryImpl.findId(email, password);

         long authorId = author.get(VISITOR_INDEX).getId();
        System.out.println(authorId);
         String role = author.get(VISITOR_INDEX).getRole();
       return authorRepositoryImpl.getAuthorPosts(authorId,role);
    }

    public boolean register(RegisterDto registerDto) {

        Author newAuthor = new Author();
        newAuthor.setName(registerDto.getName());
        newAuthor.setEmail(registerDto.getEmail());
        newAuthor.setPassword(encodePassword(registerDto.getPassword()));
        newAuthor.setRole(registerDto.getRole());

        Author author = new Author();

        author.setEmail(registerDto.getEmail());
        author.setPassword(registerDto.getPassword());
        boolean value = authorRepositoryImpl.verifyDetail(author);
        if (value == false) authorRepository.save(newAuthor);
        return value;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public String login(LoginDto loginDto) {
        Authentication authenticate =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (loginDto.getName(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        System.out.println(jwtProvider.generateToken(authenticate));
        return   jwtProvider.generateToken(authenticate);
    }

    public Optional<User> getCurrentUser() {
        User principal =(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  Optional.of(principal);
    }
}
