package com.blog.api.repositories;

import java.util.Optional;
import java.util.UUID;

import com.blog.api.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

}