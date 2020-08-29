package com.mountblue.blogpost.service;

import com.mountblue.blogpost.dto.LoginDto;
import com.mountblue.blogpost.dto.RegisterDto;
import com.mountblue.blogpost.model.Author;
import com.mountblue.blogpost.repository.AuthorRepository;
import com.mountblue.blogpost.repository.AuthorRepositoryNew;
import com.mountblue.blogpost.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceNew {

    @Autowired
    private AuthorRepositoryNew authorRepositoryNew;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    AuthorService authorService;

    public boolean register(RegisterDto registerDto) {

        Author newAuthor = new Author();
        newAuthor.setName(registerDto.getName());
        newAuthor.setEmail(registerDto.getEmail());
        newAuthor.setPassword(encodePassword(registerDto.getPassword()));
        newAuthor.setRole(registerDto.getRole());

        Author author = new Author();

        author.setEmail(registerDto.getEmail());
        author.setPassword(registerDto.getPassword());
        boolean value = authorRepository.verifyDetail(author);
            if (value == false) authorRepositoryNew.save(newAuthor);
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
