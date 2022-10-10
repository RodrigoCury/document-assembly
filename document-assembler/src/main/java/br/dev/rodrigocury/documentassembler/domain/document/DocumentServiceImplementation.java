package br.dev.rodrigocury.documentassembler.domain.document;

import br.dev.rodrigocury.documentassembler.adapter.in.utils.ProcessingType;
import br.dev.rodrigocury.documentassembler.adapter.out.document.DocumentRepository;
import br.dev.rodrigocury.documentassembler.adapter.out.document.po.DocumentPo;
import br.dev.rodrigocury.documentassembler.adapter.out.kafka.DocumentKafkaMessageProducer;
import br.dev.rodrigocury.documentassembler.application.document.dto.DocumentDto;
import br.dev.rodrigocury.documentassembler.application.document.service.DocumentService;
import br.dev.rodrigocury.documentassembler.application.document.vo.DocumentListVo;
import br.dev.rodrigocury.documentassembler.application.document.vo.DocumentVo;
import br.dev.rodrigocury.documentassembler.application.utils.exceptions.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImplementation implements DocumentService {

    private final DocumentRepository documentRepository;

    private final DocumentKafkaMessageProducer producer;

    @Override
    public Page<DocumentListVo> pageDocuments(Pageable pageable) {
        return documentRepository.listPagedDocuments(pageable);
    }

    @Override
    public DocumentVo getOneDocument(UUID documentId) {
        DocumentPo document = documentRepository
                .findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document Not Found"));

        return new DocumentVo(document);
    }

    @Override
    public DocumentVo createDocument(DocumentDto dto) {
        DocumentPo newDocumentEntity = dto.toEntity();

        documentRepository.save(newDocumentEntity);

        return new DocumentVo(newDocumentEntity);
    }

    @Override
    public void deleteDocument(UUID id) {
        boolean documentExists = documentRepository.existsById(id);

        if (!documentExists) throw new EntityNotFoundException("Document Not Found");

        producer.sendDeletionMessage(id);

        documentRepository.deleteById(id);
    }

    @Override
    public void generateDoc(UUID documentId, ProcessingType processingType) {
        DocumentPo document = documentRepository
                .findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document Not Found"));

        switch (processingType) {
            case DOCX -> producer.sendDocumentMessage(document);
            case CONSOLE -> producer.sendDocumentConsoleMessage(document);
        }
    }

}
