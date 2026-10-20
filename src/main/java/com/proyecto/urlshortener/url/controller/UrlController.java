package com.proyecto.urlshortener.url.controller;

import com.proyecto.urlshortener.url.domain.model.UrlModel;
import com.proyecto.urlshortener.url.domain.port.in.ShortenUrlUseCase;
import com.proyecto.urlshortener.url.dto.request.ShortenUrlRequest;
import com.proyecto.urlshortener.url.dto.response.ShortenUrlResponse;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UrlController {

    private final ShortenUrlUseCase shortenUrlUseCase;
    private final String baseUrl;

    public UrlController(ShortenUrlUseCase shortenUrlUseCase,
            @Value("${app.base-url}") String baseUrl) {
        this.shortenUrlUseCase = shortenUrlUseCase;
        this.baseUrl = baseUrl;
    }

    // ---------- Vistas Thymeleaf ----------

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("urlRequest", new ShortenUrlRequest());
        return "index";
    }

    @PostMapping("/shorten")
    public String shortenFromForm(@Valid @ModelAttribute("urlRequest") ShortenUrlRequest request,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        UrlModel url = shortenUrlUseCase.shorten(request.getOriginalUrl());
        model.addAttribute("shortUrl", buildShortUrl(url.getShortCode()));
        return "index";
    }

    // ---------- API REST (para Postman) ----------

    @PostMapping("/api/urls")
    public ResponseEntity<ShortenUrlResponse> shortenFromApi(
            @Valid @RequestBody ShortenUrlRequest request) {
        UrlModel url = shortenUrlUseCase.shorten(request.getOriginalUrl());
        ShortenUrlResponse body = new ShortenUrlResponse(
                url.getOriginalUrl(), buildShortUrl(url.getShortCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    // ---------- Redirección ----------

    @GetMapping("/{shortCode:[0-9a-zA-Z]+}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = shortenUrlUseCase.resolve(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    private String buildShortUrl(String shortCode) {
        return baseUrl + "/" + shortCode;
    }
}