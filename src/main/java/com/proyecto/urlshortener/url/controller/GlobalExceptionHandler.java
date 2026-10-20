package com.proyecto.urlshortener.url.controller;

import com.proyecto.urlshortener.url.domain.exception.UrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(UrlNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "not-found";
    }
}