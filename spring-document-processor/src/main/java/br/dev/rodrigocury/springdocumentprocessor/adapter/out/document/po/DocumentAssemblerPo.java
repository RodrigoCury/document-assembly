package br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.po;

import br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.po.utils.DocumentAssemblyStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class DocumentAssemblerPo {

    @Id
    String id;

    UUID documentId;

    String documentName;

    UUID s3ReferenceId;

    LocalDateTime startDate;

    LocalDateTime lastUpdateDate;

    LocalDateTime finishDate;

    DocumentAssemblyStatus status;
}
