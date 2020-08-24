package com.mountblue.blogpost.service;

import com.mountblue.blogpost.UserPrincipal;
import com.mountblue.blogpost.model.Visitor;
import com.mountblue.blogpost.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Visitor visitor = userDetailRepository.findByName(userName);
        Visitor visitorEamil = userDetailRepository.findByEmail(userName);
            if(visitor ==null || visitorEamil==null)
                throw new UsernameNotFoundException("User Not Found");
        return new UserPrincipal(visitor);
    }
}
