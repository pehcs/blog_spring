package com.blog.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.blog.api.models.User;
import com.blog.api.repositories.UserRepository;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements AuthenticationProvider {

    private UserRepository userRepository;

    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        List<GrantedAuthority> grantedAuths = new ArrayList<>();

        Optional<User> userInfos = userRepository.findByUsername(username);

        Boolean encodedPassword = new BCryptPasswordEncoder(12)
                .matches(password, userInfos.get()
                        .getPassword());

        if (userInfos.isPresent() && encodedPassword) {
            return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
        }else{
            throw new BadCredentialsException("Username not found.");
            // return null;
        }
        // validate and do your additionl logic and set the role type after your
        // validation. in this code i am simply adding admin role type
        // grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
