create table research_session_header (
    id             varchar(64) primary key,
    masalah_question varchar(2000) not null,
    created_at     timestamptz not null
);

create table revision_event (
    id                        varchar(36) primary key,
    session_id                varchar(64) not null references research_session_header(id),
    sequence                  bigint not null,
    action_type               varchar(32) not null,
    actor                     varchar(16) not null,
    actor_id                  varchar(64) not null,
    affected_block_id         varchar(64) not null,
    block_json                text,
    occurred_at               timestamptz not null,
    note                      varchar(2000),
    completeness              double precision,
    ready_for_scholar_review  boolean,
    constraint uq_revision_event_session_sequence unique (session_id, sequence)
);

create index ix_revision_event_session on revision_event (session_id, sequence);
