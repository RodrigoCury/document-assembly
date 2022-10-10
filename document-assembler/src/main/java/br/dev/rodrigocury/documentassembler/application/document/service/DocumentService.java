package br.dev.rodrigocury.documentassembler.application.document.service;

import br.dev.rodrigocury.documentassembler.adapter.in.utils.ProcessingType;
import br.dev.rodrigocury.documentassembler.application.document.dto.DocumentDto;
import br.dev.rodrigocury.documentassembler.application.document.vo.DocumentListVo;
import br.dev.rodrigocury.documentassembler.application.document.vo.DocumentVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DocumentService {

    Page<DocumentListVo> pageDocuments(Pageable pageable);

    DocumentVo getOneDocument(UUID id);

    DocumentVo createDocument(DocumentDto dto);

    void deleteDocument(UUID id);

    void generateDoc(UUID id, ProcessingType type);

}
