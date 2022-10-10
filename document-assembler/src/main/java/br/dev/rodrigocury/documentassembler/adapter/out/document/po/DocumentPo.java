package br.dev.rodrigocury.documentassembler.adapter.out.document.po;

import br.dev.rodrigocury.documentassembler.application.document.Document;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_document")
public class DocumentPo extends Document<BranchPo, LeafPo> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "document_name")
    private String documentName;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = BranchPo.class, orphanRemoval = true, optional = false)
    @JoinColumn(name = "initial_branch_id", nullable = false, referencedColumnName = "id")
    private BranchPo initialBranch;

    @Column
    @CreatedDate
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    LocalDateTime updatedAt;

}
