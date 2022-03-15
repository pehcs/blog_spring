package com.blog.api.services;

import com.blog.api.models.PrincipalUser;
import com.blog.api.models.User;
import com.blog.api.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);

        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
        if(user.getRoleList()==null){
            user.setRoles("USER");
        }        
    
        PrincipalUser userPrincipal = new PrincipalUser(user);

        return userPrincipal;
    }
}