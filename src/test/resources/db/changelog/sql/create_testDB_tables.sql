-- auto-generated definition
create table testDB.user
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

-- auto-generated definition
create table testDB.roles
(
    id                int auto_increment
        primary key,
    rolename          varchar(50) charset utf8  null,
    role_display_name varchar(100) charset utf8 null,
    `order`           int(5)                    null
)
    charset = utf8mb4;

ALTER TABLE testDB.user
    ADD COLUMN is_active tinyint(1) default 1                 null;

INSERT INTO testDB.user (username, password, email, firstname, lastname, role_id, is_active)
VALUES ('MEKCC', 12345678, 'maks@gmail.com', 'Maksym', 'Surname', 1, 1);

INSERT INTO testDB.roles (rolename, role_display_name, `order`)
VALUES ('Administrator', 'Administrator', 1);
