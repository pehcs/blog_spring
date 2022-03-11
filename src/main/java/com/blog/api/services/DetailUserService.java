package com.blog.api.services;

import java.util.Optional;

import com.blog.api.data.UserData;
import com.blog.api.models.User;
import com.blog.api.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DetailUserService implements UserDetailsService{

    private UserRepository userRepository;

    public DetailUserService(UserRepository repository) {
        this.userRepository = repository;
    }   

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }    

        return new UserData(user);
    }
    
}
