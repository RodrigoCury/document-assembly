package br.dev.rodrigocury.springdocumentprocessor.domain;

import br.dev.rodrigocury.springdocumentprocessor.adapter.in.utils.ProcessingType;
import br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.po.DocumentAssemblerPo;
import br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.po.utils.ProcessingStatus;
import br.dev.rodrigocury.springdocumentprocessor.adapter.out.s3.S3Repository;
import br.dev.rodrigocury.springdocumentprocessor.application.document.BranchImpl;
import br.dev.rodrigocury.springdocumentprocessor.application.document.DocumentImpl;
import br.dev.rodrigocury.springdocumentprocessor.domain.helper.DocumentConsumerHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentConsumerService {

    private final DocumentAssemblyStatusService statusService;
    private final DocumentConsumerHelper helper;
    private final S3Repository s3Repository;

    public void consumeDocument(DocumentImpl docToProcess, ProcessingType processingType) {
        switch (processingType) {
            case DOCX -> {
                try {
                    toDocX(docToProcess);
                } catch (IOException ex) {
                    log.error("Error Creating Docx", ex);
                }
            }
            case CONSOLE -> toConsole(docToProcess);
        }
    }

    private void toConsole(DocumentImpl docToProcess) {
        statusService.startTrackingConsole(docToProcess.getId());

        AtomicInteger totalBranches = new AtomicInteger();
        AtomicInteger branchesAlreadyProcessed = new AtomicInteger();

        docToProcess.consume(document -> {
            LinkedList<BranchImpl> branches = helper.parseThroughBranches(totalBranches, (DocumentImpl) document);

            branches.forEach(branch -> {
                statusService.updateStatusConsole(totalBranches.get(), branchesAlreadyProcessed.get(), docToProcess.getId());

                branch.consume(helper.printLeaves(), null);

                branchesAlreadyProcessed.incrementAndGet();
            });

            statusService.finishConsole(docToProcess.getId());
        });
    }

    private void toDocX(DocumentImpl docToProcess) throws IOException {
        DocumentAssemblerPo tracker = statusService.startTracking(docToProcess.getId(), docToProcess.getDocumentName());

        AtomicInteger totalBranches = new AtomicInteger();
        AtomicInteger branchesAlreadyProcessed = new AtomicInteger();

        XWPFDocument xwpfDocumentGenerated = docToProcess.produce((document) -> {
            LinkedList<BranchImpl> branches = helper.parseThroughBranches(totalBranches, (DocumentImpl) document);

            XWPFDocument xwpfDocument = new XWPFDocument();

            helper.createFirstPage(docToProcess, xwpfDocument);

            branches.forEach(branch -> {
                statusService.updateStatus(tracker, ProcessingStatus.PROCESSING, totalBranches.get(), branchesAlreadyProcessed.get());

                branch.consume(
                    helper.leavesToDocX(xwpfDocument),
                    helper.branchToDocX(xwpfDocument)
                );

                branchesAlreadyProcessed.incrementAndGet();
            });

            return xwpfDocument;
        });

        UUID fileKey = uploadToS3(tracker, totalBranches, branchesAlreadyProcessed, xwpfDocumentGenerated);

        statusService.finish(tracker, fileKey);
    }

    private UUID uploadToS3(DocumentAssemblerPo tracker, AtomicInteger totalBranches, AtomicInteger branchesAlreadyProcessed, XWPFDocument xwpfDocumentGenerated) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        xwpfDocumentGenerated.write(outputStream);

        byte[] bytes = outputStream.toByteArray();

        return s3Repository.uploadFile(bytes);
    }

}
