package com.project.management.user.repository;

import com.project.management.user.entity.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, UUID> {

    //List<PasswordHistory> findByUser_UserId(UUID userId);
}
