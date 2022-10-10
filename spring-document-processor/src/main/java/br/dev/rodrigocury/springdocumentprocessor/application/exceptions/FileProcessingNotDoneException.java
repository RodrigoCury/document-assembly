package br.dev.rodrigocury.springdocumentprocessor.application.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.nio.charset.Charset;

public class FileProcessingNotDoneException extends HttpStatusCodeException {

    public FileProcessingNotDoneException() {
        super(HttpStatus.FAILED_DEPENDENCY, "File is not processed yet");
    }

    public FileProcessingNotDoneException(String statusText) {
        super(HttpStatus.NOT_FOUND, statusText);
    }

    public FileProcessingNotDoneException(String statusText, byte[] responseBody, Charset responseCharset) {
        super(HttpStatus.NOT_FOUND, statusText, responseBody, responseCharset);
    }

    public FileProcessingNotDoneException(String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(HttpStatus.NOT_FOUND, statusText, responseHeaders, responseBody, responseCharset);
    }

    public FileProcessingNotDoneException(String message, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(message, HttpStatus.NOT_FOUND, statusText, responseHeaders, responseBody, responseCharset);
    }
}
