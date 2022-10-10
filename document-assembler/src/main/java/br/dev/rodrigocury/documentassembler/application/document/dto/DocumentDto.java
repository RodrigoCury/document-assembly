package br.dev.rodrigocury.documentassembler.application.document.dto;

import br.dev.rodrigocury.documentassembler.adapter.out.document.po.DocumentPo;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {

    @NotNull @NotBlank
    String documentName;

    @NotNull @Valid
    BranchDto initialBranch;

    public DocumentPo toEntity() {
        return DocumentPo.builder()
                .documentName(documentName)
                .initialBranch(initialBranch.toEntity())
                .build();
    }
}
