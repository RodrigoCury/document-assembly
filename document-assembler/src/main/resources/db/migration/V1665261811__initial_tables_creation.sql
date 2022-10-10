create table tbl_doc_branches (
    id uuid PRIMARY KEY,
    next_branch_id uuid references tbl_doc_branches(id),
    created_at timestamp NOT NULL,
    updated_at timestamp
);

create table tbl_document (
    id uuid PRIMARY KEY,
    document_name varchar NOT NULL,
    initial_branch_id uuid NOT NULL references tbl_doc_branches(id),
    created_at timestamp NOT NULL,
    updated_at timestamp
);

create table tbl_branch_leaf(
    id uuid PRIMARY KEY,
    text varchar NOT NULL,
    leaf_branch_id uuid NOT NULL references tbl_doc_branches(id),
    created_at timestamp NOT NULL,
    updated_at timestamp
)