create table resume
(
    uuid      char(36) not null primary key,
    full_name text     not null
);

alter table resume
    owner to postgres;

create table contact
(
    id          serial,
    resume_uuid char(36) not null constraint contact_resume_uuid_fk references resume (uuid) on delete cascade,
    type        text     not null,
    value       text     not null
);

alter table contact
    owner to postgres;

create unique index contact_uuid_type_index
    on contact (resume_uuid, type);

CREATE TABLE section
(
    id          serial primary key,
    resume_uuid char(36) not null references resume (uuid) on delete cascade,
    type        text     not null,
    content     text     not null
);

alter table section
    owner to postgres;

create unique index section_idx
    on section (resume_uuid, type);