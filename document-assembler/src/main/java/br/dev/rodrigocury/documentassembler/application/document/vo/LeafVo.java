package br.dev.rodrigocury.documentassembler.application.document.vo;

import br.dev.rodrigocury.documentassembler.adapter.out.document.po.LeafPo;
import br.dev.rodrigocury.documentassembler.application.utils.BaseVo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class LeafVo extends BaseVo {
    UUID id;
    String text;

    LeafVo(LeafPo leafPo) {
        id = leafPo.getId();
        text = leafPo.getText();
        createdAt = leafPo.getCreatedAt();
        updatedAt = leafPo.getUpdatedAt();
    }

}
