USE testDB;

CREATE TABLE user
(
    id       bigint(20)  NOT NULL,
    email    varchar(30) NOT NULL,
    username varchar(40) NOT NULL,
    password varchar(8)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);

CREATE TABLE role
(
    id   bigint(20)  NOT NULL,
    role_name varchar(15) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    user_id bigint(20) NOT NULL,
    role_id bigint(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    PRIMARY KEY (user_id, role_id)
);