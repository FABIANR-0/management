package com.proyecto.demo.user.repository;

import com.proyecto.demo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>,UserRepositoryCustom {
    Optional<User> findByUserName(String userName);

    Optional<User> findByUserIdAndUserName(UUID userId,String userName);
}
