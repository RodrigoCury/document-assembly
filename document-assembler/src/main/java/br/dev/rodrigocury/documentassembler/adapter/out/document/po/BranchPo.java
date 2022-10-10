package br.dev.rodrigocury.documentassembler.adapter.out.document.po;

import br.dev.rodrigocury.documentassembler.application.document.Branch;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_doc_branches")
public class BranchPo extends Branch<LeafPo> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @OneToMany(targetEntity = LeafPo.class, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "branch")
    @JsonManagedReference
    private List<LeafPo> leaves;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "next_branch_id")
    private BranchPo nextBranch;

    @Column
    @CreatedDate
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    LocalDateTime updatedAt;
}
