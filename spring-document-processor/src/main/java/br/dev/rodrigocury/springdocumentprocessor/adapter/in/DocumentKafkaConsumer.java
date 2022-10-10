package br.dev.rodrigocury.springdocumentprocessor.adapter.in;

import br.dev.rodrigocury.springdocumentprocessor.application.document.DocumentImpl;
import br.dev.rodrigocury.springdocumentprocessor.domain.DocumentConsumerService;
import br.dev.rodrigocury.springdocumentprocessor.domain.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static br.dev.rodrigocury.springdocumentprocessor.adapter.in.utils.ProcessingType.CONSOLE;
import static br.dev.rodrigocury.springdocumentprocessor.adapter.in.utils.ProcessingType.DOCX;

@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentKafkaConsumer {

    private final DocumentConsumerService service;

    private final DocumentService documentService;

    @KafkaListener(
        topics = "document-console",
        containerFactory = "documentKafkaListenerContainerFactory"
    )
    public void documentConsoleListener(DocumentImpl message) {
        log.info(String.format("Message received: %s", message.getId().toString()));
        service.consumeDocument(message, CONSOLE);
    }

    @KafkaListener(
        topics = "document",
        containerFactory = "documentKafkaListenerContainerFactory"
    )
    public void documentDocxListener(DocumentImpl message) {
        log.info(String.format("Message received: %s", message.getId().toString()));
        service.consumeDocument(message, DOCX);
    }

    @KafkaListener(
        topics = "document-deletion",
        containerFactory = "deletionKafkaListenerContainerFactory"
    )
    public void documentDeletionListener(UUID message) {
        log.info(String.format("Message received: deleting files from: %s", message));
        documentService.deleteDocumentFiles(message);
    }
}
