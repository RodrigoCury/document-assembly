package br.dev.rodrigocury.springdocumentprocessor.application.document;


import br.dev.rodrigocury.springdocumentprocessor.application.document.interfaces.Branch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
@Setter
@NoArgsConstructor
public class BranchImpl extends Branch<LeafImpl> {
    protected BranchImpl nextBranch;

    public void consume(Consumer<LeafImpl> leafConsumer, Consumer<BranchImpl> branchConsumer) {
        if (leafConsumer != null) leaves.forEach(leafConsumer);
        if (branchConsumer != null) branchConsumer.accept(this);
    }
}
