package br.dev.rodrigocury.springdocumentprocessor.domain.helper;

import br.dev.rodrigocury.springdocumentprocessor.application.document.BranchImpl;
import br.dev.rodrigocury.springdocumentprocessor.application.document.DocumentImpl;
import br.dev.rodrigocury.springdocumentprocessor.application.document.LeafImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
@Service
public class DocumentConsumerHelper {

    public Consumer<BranchImpl> branchToDocX(XWPFDocument xwpfDocument) {
        return branch -> {
            XWPFParagraph paragraph = xwpfDocument.createParagraph();
            paragraph.setPageBreak(true);
        };
    }

    public Consumer<LeafImpl> leavesToDocX(XWPFDocument xwpfDocument) {
        return leaf -> {
            XWPFParagraph leafParagraph = xwpfDocument.createParagraph();
            XWPFRun run = leafParagraph.createRun();
            run.setText(leaf.toString());
        };
    }

    public void createFirstPage(DocumentImpl docToProcess, XWPFDocument xwpfDocument) {
        XWPFParagraph firstPageParagraph = xwpfDocument.createParagraph();
        firstPageParagraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = firstPageParagraph.createRun();
        run.setBold(true);
        run.setText(docToProcess.getDocumentName());
        run.addBreak(BreakType.PAGE);
    }

    public Consumer<LeafImpl> printLeaves() {
        return leaf -> {
            log.info(leaf.toString());
        };
    }

    public LinkedList<BranchImpl> parseThroughBranches(AtomicInteger totalBranches, DocumentImpl document) {
        LinkedList<BranchImpl> branches = new LinkedList<>();

        BranchImpl currentBranch = document.getInitialBranch();

        do {
            branches.add(currentBranch);
            totalBranches.getAndIncrement();
            currentBranch = currentBranch.getNextBranch();
        } while (currentBranch != null);

        return branches;
    }

}
