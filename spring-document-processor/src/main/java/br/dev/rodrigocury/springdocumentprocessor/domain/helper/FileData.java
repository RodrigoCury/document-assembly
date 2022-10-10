package br.dev.rodrigocury.springdocumentprocessor.domain.helper;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

@Data
@RequiredArgsConstructor
public class FileData {
    private final InputStream inputStream;
    private final String documentName;
}
