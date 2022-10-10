package br.dev.rodrigocury.springdocumentprocessor.application.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.nio.charset.Charset;

public class EntityNotFoundException extends HttpStatusCodeException {

    public EntityNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public EntityNotFoundException(String statusText) {
        super(HttpStatus.NOT_FOUND, statusText);
    }

    public EntityNotFoundException(String statusText, byte[] responseBody, Charset responseCharset) {
        super(HttpStatus.NOT_FOUND, statusText, responseBody, responseCharset);
    }

    public EntityNotFoundException(String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(HttpStatus.NOT_FOUND, statusText, responseHeaders, responseBody, responseCharset);
    }

    public EntityNotFoundException(String message, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(message, HttpStatus.NOT_FOUND, statusText, responseHeaders, responseBody, responseCharset);
    }
}
