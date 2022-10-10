package br.dev.rodrigocury.springdocumentprocessor.adapter.in;

import br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.po.DocumentAssemblerPo;
import br.dev.rodrigocury.springdocumentprocessor.domain.DocumentService;
import br.dev.rodrigocury.springdocumentprocessor.domain.helper.FileData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.UUID;

import static org.springframework.util.FileCopyUtils.BUFFER_SIZE;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;

    @GetMapping("document-files/{documentId}")
    public ResponseEntity<List<DocumentAssemblerPo>> getDocumentFiles(@PathVariable UUID documentId) {
        return ResponseEntity.ok(service.getDocumentFiles(documentId));
    }

    @GetMapping("{fileId}")
    public StreamingResponseBody downloadDocumentId(HttpServletResponse response, @PathVariable String fileId) {
        FileData fileData = service.getFile(fileId);

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileData.getDocumentName() + "\"");

        return outputStream -> {
            int bytesRead;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = fileData.getInputStream().read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        };
    }
}
