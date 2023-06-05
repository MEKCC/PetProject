-- auto-generated definition
create table roles
(
    id                int auto_increment
        primary key,
    rolename          varchar(50) charset utf8  null,
    role_display_name varchar(100) charset utf8 null,
    `order`           int(5)                    null
)
    charset = utf8mb4;

