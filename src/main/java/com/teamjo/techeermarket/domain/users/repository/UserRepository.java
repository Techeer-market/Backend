package com.teamjo.techeermarket.domain.users.repository;

import com.teamjo.techeermarket.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<Users, Long> {

//    Users findByEmail(String email);
    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);
}
