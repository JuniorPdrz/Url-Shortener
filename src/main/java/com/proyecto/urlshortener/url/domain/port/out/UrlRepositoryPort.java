package com.proyecto.urlshortener.url.domain.port.out;

import com.proyecto.urlshortener.url.domain.model.UrlModel;
import java.util.Optional;

public interface UrlRepositoryPort {

    UrlModel save(UrlModel url);

    Optional<UrlModel> findByShortCode(String shortCode);
}
