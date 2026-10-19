package com.proyecto.urlshortener.url.domain.exception;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException(String shortCode) {
        super("No existe una URL para el código: " + shortCode);
    }
}