package br.dev.rodrigocury.documentassembler.application.document.vo;

import br.dev.rodrigocury.documentassembler.adapter.out.document.po.BranchPo;
import br.dev.rodrigocury.documentassembler.application.utils.BaseVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class BranchVo extends BaseVo {
    UUID id;
    List<LeafVo> leaves;
    BranchVo nextBranch;

    BranchVo(BranchPo branch) {
        this.id = branch.getId();
        this.leaves = branch.getLeaves().stream().map(LeafVo::new).toList();

        BranchPo nextBranchEntity = branch.getNextBranch();

        this.nextBranch = nextBranchEntity != null ? new BranchVo(nextBranchEntity): null;
        this.createdAt = branch.getCreatedAt();
        this.updatedAt = branch.getUpdatedAt();
    }
}
