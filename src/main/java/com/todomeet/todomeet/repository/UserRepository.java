package com.todomeet.todomeet.repository;

import com.todomeet.todomeet.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long>
{

    Optional<UserEntity> findByRefreshToken(String refreshToken);
}
