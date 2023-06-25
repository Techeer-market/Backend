package com.teamjo.techeermarket.domain.users.repository;

import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmailAndSocial(String email, String social);

    Optional<Users> findByEmailAndSocialAndIsDeletedIsFalse(String email, String social);

    Optional<Users> findById(Long id);

    Users findByEmail(String email);

    boolean existsByEmailAndSocial(String email, String social);

    Users findByUserUuid(UUID userUuid);


}
