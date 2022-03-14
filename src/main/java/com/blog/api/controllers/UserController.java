package com.blog.api.controllers;

import com.blog.api.models.User;
import com.blog.api.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired    
    private UserRepository userRepository;


    @GetMapping
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody User user){
        User userExists = userRepository.findByUsername(user.getUsername());
        if(userExists == null){
            String passwordEncripted = new BCryptPasswordEncoder(12).encode(user.getPassword());
            user.setPassword(passwordEncripted);
            return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }

    }

}

