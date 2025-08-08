package com.project.management.user.repository;

import com.project.management.user.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio que gestiona las operaciones de persistencia para la entidad RefreshToken.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    /**
     * Busca un token de actualización por su valor.
     *
     * @param token el valor del token de actualización
     * @return una instancia de RefreshToken envuelta en un Optional
     */
    Optional<RefreshToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query(value = "delete from RefreshToken  r where r.user.userId = :id")
    void deleteByUser_UserId(@Param("id") UUID userId);
}
