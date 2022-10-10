package br.dev.rodrigocury.springdocumentprocessor.application.document;

import br.dev.rodrigocury.springdocumentprocessor.application.document.interfaces.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class DocumentImpl extends Document<BranchImpl, LeafImpl> {
    private UUID id;

    private String documentName;



}

