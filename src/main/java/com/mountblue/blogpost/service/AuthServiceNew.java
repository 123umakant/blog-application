package com.mountblue.blogpost.service;

import com.mountblue.blogpost.dto.LoginRegister;
import com.mountblue.blogpost.dto.RegisterRequest;
import com.mountblue.blogpost.model.Author;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    public void register(RegisterRequest registerRequest) {
        Author newAuthor = new Author();
        newAuthor.setName(registerRequest.getName());
        newAuthor.setEmail(registerRequest.getEmail());
        newAuthor.setPassword(encodePassword(registerRequest.getPassword()));
        authorRepositoryNew.save(newAuthor);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public String login(LoginRegister loginRegister) {
        Authentication authenticate =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (loginRegister.getName(),loginRegister.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        System.out.println(jwtProvider.generateToken(authenticate));
        return   jwtProvider.generateToken(authenticate);
    }

    public Optional<User> getCurrentUser() {

        User principal =(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  Optional.of(principal);
    }
}
