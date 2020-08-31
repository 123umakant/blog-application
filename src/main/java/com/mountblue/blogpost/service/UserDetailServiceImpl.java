package com.mountblue.blogpost.service;


import com.mountblue.blogpost.model.Author;
import com.mountblue.blogpost.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
      Author author = authorRepository.findByName(name).orElseThrow(() ->
              new UsernameNotFoundException("No Author Found :" + name));
        return new User(author.getName(),author.getPassword(),true,true,true,true,
                getAuthorities("ROLE_USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role_author) {
        return Collections.singletonList(new SimpleGrantedAuthority(role_author));
    }
}
