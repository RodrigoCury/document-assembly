package br.dev.rodrigocury.springdocumentprocessor.adapter.out.document;

import br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.po.DocumentAssemblerPo;
import br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.po.utils.ProcessingStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DocumentAssemblerRepository extends MongoRepository<DocumentAssemblerPo, String> {
    List<DocumentAssemblerPo> findAllByDocumentId(UUID documentId);

    List<DocumentAssemblerPo> findAllByDocumentIdAndStatus_CurrentStatus(UUID documentId, ProcessingStatus status_currentStatus);

    void deleteAllByDocumentId(UUID documentId);
}
