package br.dev.rodrigocury.documentassembler.adapter.in.document;

import br.dev.rodrigocury.documentassembler.adapter.in.utils.ProcessingType;
import br.dev.rodrigocury.documentassembler.adapter.out.kafka.DocumentKafkaMessageProducer;
import br.dev.rodrigocury.documentassembler.application.document.dto.DocumentDto;
import br.dev.rodrigocury.documentassembler.application.document.service.DocumentService;
import br.dev.rodrigocury.documentassembler.application.document.vo.DocumentListVo;
import br.dev.rodrigocury.documentassembler.application.document.vo.DocumentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "document")
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping
    public ResponseEntity<Page<DocumentListVo>> pageDocuments(
        @PageableDefault Pageable pageable
    ) {
        Page<DocumentListVo> documentListVos = documentService.pageDocuments(pageable);

        return ResponseEntity.ok(documentListVos);
    }

    @GetMapping("{documentId}")
    public ResponseEntity<DocumentVo> getOneDocument(
        @PathVariable UUID documentId
    ) {
        DocumentVo documentVo = documentService.getOneDocument(documentId);

        return ResponseEntity.ok(documentVo);
    }

    @PostMapping
    public ResponseEntity<DocumentVo> createDocument(
        @RequestBody @Valid DocumentDto documentDto
    ) {
        DocumentVo documentVo = documentService.createDocument(documentDto);

        URI uri = URI.create(String.format("/api/da/document/%s", documentVo.getId().toString()));

        return ResponseEntity
                .created(uri)
                .body(documentVo);
    }

    @DeleteMapping("{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable UUID documentId) {
        documentService.deleteDocument(documentId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("process/{documentId}")
    public ResponseEntity<?> processDocument(
        @PathVariable
        UUID documentId,
        @RequestParam(required = false, defaultValue = "CONSOLE")
        ProcessingType processingType
    ) {
        documentService.generateDoc(documentId, processingType);
        return ResponseEntity.ok().build();
    }
}
