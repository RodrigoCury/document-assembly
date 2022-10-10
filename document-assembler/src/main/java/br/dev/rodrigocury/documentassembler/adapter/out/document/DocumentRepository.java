package br.dev.rodrigocury.documentassembler.adapter.out.document;


import br.dev.rodrigocury.documentassembler.adapter.out.document.po.DocumentPo;
import br.dev.rodrigocury.documentassembler.application.document.vo.DocumentListVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface DocumentRepository extends PagingAndSortingRepository<DocumentPo, UUID> {

    @Query(value = """
        SELECT new br.dev.rodrigocury.documentassembler.application.document.vo.DocumentListVo(dpo.id, dpo.documentName)
        FROM tbl_document as dpo
    """)
    Page<DocumentListVo> listPagedDocuments(Pageable pageable);

}
