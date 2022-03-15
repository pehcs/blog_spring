package com.blog.api.controllers;

import com.blog.api.models.Posts;
import com.blog.api.models.User;
import com.blog.api.repositories.PostsRepository;
import com.blog.api.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostsController {
    
    @Autowired
    public PostsRepository postRepository;
    public UserRepository userRepository;

    @PostMapping("/{user}")
    public ResponseEntity<Object> savePostInUser(@PathVariable User user, @RequestBody Posts post){
        User username = userRepository.findByUsername(user.getUsername());

        if(username==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }

        
        Posts newPost = new Posts(
            post.getTitle(), post.getBody(), user
        );

        
        postRepository.save(newPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }
}
