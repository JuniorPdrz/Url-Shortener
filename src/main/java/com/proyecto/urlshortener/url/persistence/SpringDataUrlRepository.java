package com.proyecto.urlshortener.url.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUrlRepository extends JpaRepository<UrlEntity, Long> {

    Optional<UrlEntity> findByShortCode(String shortCode);
}