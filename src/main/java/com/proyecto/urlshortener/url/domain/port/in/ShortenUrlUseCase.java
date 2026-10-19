package com.proyecto.urlshortener.url.domain.port.in;

import com.proyecto.urlshortener.url.domain.model.UrlModel;

public interface ShortenUrlUseCase {

    UrlModel shorten(String originalUrl);

    String resolve(String shortCode);
}