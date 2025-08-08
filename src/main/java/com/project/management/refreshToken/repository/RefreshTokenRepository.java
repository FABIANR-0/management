package com.project.management.refreshToken.repository;

import com.project.management.refreshToken.entity.RefreshToken;
import com.project.management.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(UUID token);
    RefreshToken findByUser(User user);
}
