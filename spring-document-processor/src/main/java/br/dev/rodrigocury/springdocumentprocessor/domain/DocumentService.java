package br.dev.rodrigocury.springdocumentprocessor.domain;

import br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.DocumentAssemblerRepository;
import br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.po.DocumentAssemblerPo;
import br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.po.utils.ProcessingStatus;
import br.dev.rodrigocury.springdocumentprocessor.adapter.out.s3.S3Repository;
import br.dev.rodrigocury.springdocumentprocessor.application.exceptions.EntityNotFoundException;
import br.dev.rodrigocury.springdocumentprocessor.application.exceptions.FileProcessingNotDoneException;
import br.dev.rodrigocury.springdocumentprocessor.domain.helper.FileData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentAssemblerRepository repository;

    private final S3Repository s3Repository;

    public FileData getFile(String fileId) {
        DocumentAssemblerPo fileRef = repository.findById(fileId).orElseThrow(() -> new EntityNotFoundException("File not found"));

        if (fileRef.getStatus().getCurrentStatus() != ProcessingStatus.FINISHED) {
            throw new FileProcessingNotDoneException();
        }

        InputStream inputStream = s3Repository.getFile(fileRef.getS3ReferenceId());

        return new FileData(inputStream, fileRef.getDocumentName());
    }

    public List<DocumentAssemblerPo> getDocumentFiles(UUID documentId) {
        return repository.findAllByDocumentId(documentId);
    }

    public void deleteDocumentFiles(UUID documentId) {
        List<DocumentAssemblerPo> files = repository.findAllByDocumentIdAndStatus_CurrentStatus(documentId, ProcessingStatus.FINISHED);

        if (files.isEmpty()) return;

        List<UUID> s3References = files.stream().map(DocumentAssemblerPo::getS3ReferenceId).toList();

        s3Repository.deleteFiles(s3References);

        repository.deleteAllByDocumentId(documentId);
    }
}
