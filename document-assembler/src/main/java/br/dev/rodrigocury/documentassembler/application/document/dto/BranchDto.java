package br.dev.rodrigocury.documentassembler.application.document.dto;

import br.dev.rodrigocury.documentassembler.adapter.out.document.po.BranchPo;
import br.dev.rodrigocury.documentassembler.adapter.out.document.po.LeafPo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class BranchDto {
    @NotEmpty @NotNull
    List<@NotNull @NotBlank String> leaves;

    @Valid
    BranchDto nextBranch;

    public BranchPo toEntity() {
        BranchPo nextBranchEntity = (nextBranch != null) ? nextBranch.toEntity(): null;

        BranchPo branchPo = BranchPo.builder()
                .nextBranch(nextBranchEntity)
                .build();

        List<LeafPo> leavesEntity = leaves
                .stream()
                .map(leaf -> LeafPo.builder()
                        .text(leaf)
                        .branch(branchPo)
                        .build()
                )
                .toList();

        branchPo.setLeaves(leavesEntity);

        return branchPo;
    }
}
