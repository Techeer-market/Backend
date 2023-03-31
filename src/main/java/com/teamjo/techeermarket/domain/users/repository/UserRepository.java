package com.teamjo.techeermarket.domain.users.repository;

import com.teamjo.techeermarket.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmailAndSocial(String email, String social);

<<<<<<< refs/remotes/origin/develop
    Optional<Users> findByEmailAndSocialAndIsDeletedIsFalse(String email, String social);

    boolean existsByEmailAndSocial(String email, String social);
=======
>>>>>>> (TM-14) 게시물 작성 : 에러 있음

}
