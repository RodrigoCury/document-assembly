package br.dev.rodrigocury.documentassembler.application.document.vo;

import br.dev.rodrigocury.documentassembler.application.utils.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DocumentListVo extends BaseVo {
    UUID id;
    String documentName;
}