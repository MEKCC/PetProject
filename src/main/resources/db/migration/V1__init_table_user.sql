-- auto-generated definition
create table user
(
    id                   bigint auto_increment primary key,
    username             varchar(360) charset utf8 null,
    password             varchar(360) charset utf8 null,
    email                varchar(45) charset utf8  null unique,
    firstname            varchar(45) charset utf8  null,
    lastname             varchar(45) charset utf8  null,
    role_id              int        default 2      not null,
    createddate          datetime                  null,
    createdby            int unsigned              null,
    modifieddate         datetime                  null,
    modifiedby           int unsigned              null,
    password_reset_token varchar(765) charset utf8 null
)
    charset = utf8mb4;

create index fk_created_ky_idx
    on user (createdby);

create index fk_modified_by_idx
    on user (modifiedby);