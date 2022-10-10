package br.dev.rodrigocury.documentassembler.application.document.vo;

import br.dev.rodrigocury.documentassembler.adapter.out.document.po.DocumentPo;
import br.dev.rodrigocury.documentassembler.application.utils.BaseVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class DocumentVo extends BaseVo {
    UUID id;
    String documentName;
    BranchVo initialBranch;

    public DocumentVo(DocumentPo documentPo) {
        this.id = documentPo.getId();
        this.documentName = documentPo.getDocumentName();
        this.initialBranch = new BranchVo(documentPo.getInitialBranch());
        this.createdAt = documentPo.getCreatedAt();
        this.updatedAt = documentPo.getUpdatedAt();
    }
}
