package com.proyecto.urlshortener.url.persistence;

import com.proyecto.urlshortener.url.domain.model.UrlModel;
import com.proyecto.urlshortener.url.domain.port.out.UrlRepositoryPort;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UrlRepositoryAdapter implements UrlRepositoryPort {

    private final SpringDataUrlRepository repository;

    public UrlRepositoryAdapter(SpringDataUrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public UrlModel save(UrlModel url) {
        UrlEntity saved = repository.save(toEntity(url));
        return toModel(saved);
    }

    @Override
    public Optional<UrlModel> findByShortCode(String shortCode) {
        return repository.findByShortCode(shortCode).map(this::toModel);
    }

    private UrlEntity toEntity(UrlModel model) {
        UrlEntity entity = new UrlEntity();
        entity.setId(model.getId());
        entity.setOriginalUrl(model.getOriginalUrl());
        entity.setShortCode(model.getShortCode());
        entity.setCreatedAt(model.getCreatedAt());
        return entity;
    }

    private UrlModel toModel(UrlEntity entity) {
        return new UrlModel(
                entity.getId(),
                entity.getOriginalUrl(),
                entity.getShortCode(),
                entity.getCreatedAt());
    }
}