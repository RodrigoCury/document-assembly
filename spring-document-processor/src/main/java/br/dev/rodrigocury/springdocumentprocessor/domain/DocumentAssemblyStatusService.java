package br.dev.rodrigocury.springdocumentprocessor.domain;

import br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.DocumentAssemblerRepository;
import br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.po.DocumentAssemblerPo;
import br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.po.utils.DocumentAssemblyStatus;
import br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.po.utils.ProcessingStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentAssemblyStatusService {

    private final DocumentAssemblerRepository repository;

    public void startTrackingConsole(UUID documentId) {
        log.info(String.format("Starting to consume document: %s", documentId.toString()));
    }

    public void updateStatusConsole( Integer total, Integer current, UUID documentId) {
        double percentage = (1.0 * current / total);

        log.info(String.format("Consuming document: %.2f%% | %s ", percentage * 100, documentId));
    }

    public void finishConsole(UUID documentId) {
        log.info(String.format("Finished Consuming Document: %s ", documentId));
    }

    public DocumentAssemblerPo startTracking(UUID documentId, String documentName) {
        log.info(String.format("Starting to consume document: %s", documentId.toString()));

        DocumentAssemblyStatus startStatus = DocumentAssemblyStatus.builder()
                .currentStatus(ProcessingStatus.STARTING)
                .processingStatus(0.0)
                .build();

        DocumentAssemblerPo tracker = DocumentAssemblerPo.builder()
                .status(startStatus)
                .documentName(documentName != null ? String.format("%s-%s.docx", documentName, LocalDateTime.now()) : null)
                .documentId(documentId)
                .startDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();

        return repository.save(tracker);
    }

    public void updateStatus(DocumentAssemblerPo statusTracker, ProcessingStatus status, Integer total, Integer current) {
        double percentage = (1.0 * current / total);

        setUpdatedStatus(status, percentage, statusTracker, null);
    }

    public void finish(DocumentAssemblerPo statusTracker, UUID s3ReferenceId) {
        statusTracker.setS3ReferenceId(s3ReferenceId);

        setUpdatedStatus(ProcessingStatus.FINISHED, 1.0, statusTracker, s3ReferenceId);

        log.info(String.format("Finished Consuming Document: %s ", statusTracker.getDocumentId()));
    }

    private void setUpdatedStatus(ProcessingStatus status, double percentage, DocumentAssemblerPo statusTracker, UUID s3ReferenceId) {
        log.info(String.format("Consuming document: %.2f%% | %s ", percentage * 100, statusTracker.getDocumentId()));

        if (status == ProcessingStatus.FINISHED && s3ReferenceId == null) {
            repository.delete(statusTracker);
        } else {
            statusTracker.setLastUpdateDate(LocalDateTime.now());

            DocumentAssemblyStatus currentStatus = statusTracker.getStatus();
            currentStatus.setCurrentStatus(status);
            currentStatus.setProcessingStatus(percentage);

            repository.save(statusTracker);
        }

    }

}
