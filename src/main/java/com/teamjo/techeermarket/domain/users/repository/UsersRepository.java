package com.teamjo.techeermarket.domain.users.repository;

import com.teamjo.techeermarket.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
