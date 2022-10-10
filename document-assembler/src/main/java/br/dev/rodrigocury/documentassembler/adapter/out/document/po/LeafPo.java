package br.dev.rodrigocury.documentassembler.adapter.out.document.po;

import br.dev.rodrigocury.documentassembler.application.document.Leaf;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_branch_leaf")
public class LeafPo extends Leaf {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "text")
    private String text;

    @JsonBackReference
    @JoinColumn(name = "leaf_branch_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, optional = false, targetEntity = BranchPo.class)
    private BranchPo branch;

    @Column
    @CreatedDate
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    LocalDateTime updatedAt;

}
