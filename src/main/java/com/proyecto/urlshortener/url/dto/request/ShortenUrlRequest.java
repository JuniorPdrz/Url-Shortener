package com.proyecto.urlshortener.url.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class ShortenUrlRequest {

    @NotBlank(message = "La URL es obligatoria")
    @URL(message = "La URL no es válida")
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
