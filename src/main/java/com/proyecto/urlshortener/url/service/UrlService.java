package com.proyecto.urlshortener.url.service;

import com.proyecto.urlshortener.url.domain.exception.UrlNotFoundException;
import com.proyecto.urlshortener.url.domain.model.UrlModel;
import com.proyecto.urlshortener.url.domain.port.in.ShortenUrlUseCase;
import com.proyecto.urlshortener.url.domain.port.out.UrlRepositoryPort;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UrlService implements ShortenUrlUseCase {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final UrlRepositoryPort urlRepository;

    public UrlService(UrlRepositoryPort urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    @Transactional
    public UrlModel shorten(String originalUrl) {
        // 1. Guardar sin código para obtener el id autogenerado
        UrlModel url = new UrlModel(null, originalUrl, null, LocalDateTime.now());
        UrlModel saved = urlRepository.save(url);

        // 2. Convertir el id a Base62 y guardar el código
        saved.setShortCode(encode(saved.getId()));
        return urlRepository.save(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public String resolve(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(UrlModel::getOriginalUrl)
                .orElseThrow(() -> new UrlNotFoundException(shortCode));
    }

    private String encode(long number) {
        if (number == 0) {
            return String.valueOf(BASE62.charAt(0));
        }
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            sb.append(BASE62.charAt((int) (number % 62)));
            number /= 62;
        }
        return sb.reverse().toString();
    }
}