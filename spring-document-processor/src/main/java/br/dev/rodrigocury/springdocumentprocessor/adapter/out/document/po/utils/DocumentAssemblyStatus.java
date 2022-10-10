package br.dev.rodrigocury.springdocumentprocessor.adapter.out.document.po.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DocumentAssemblyStatus {
    ProcessingStatus currentStatus;
    Double processingStatus;
}
