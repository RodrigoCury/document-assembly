package br.dev.rodrigocury.springdocumentprocessor.application.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.nio.charset.Charset;

public class UnableToDeleteException extends HttpStatusCodeException {

    public UnableToDeleteException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "We were unable to delete the files, try again later");
    }

    public UnableToDeleteException(String statusText) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, statusText);
    }

    public UnableToDeleteException(String statusText, byte[] responseBody, Charset responseCharset) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, statusText, responseBody, responseCharset);
    }

    public UnableToDeleteException(String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, statusText, responseHeaders, responseBody, responseCharset);
    }

    public UnableToDeleteException(String message, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, statusText, responseHeaders, responseBody, responseCharset);
    }
}
