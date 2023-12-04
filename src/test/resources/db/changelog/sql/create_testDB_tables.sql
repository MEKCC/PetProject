-- auto-generated definition
create table testDB.user
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    role ENUM('USER', 'ADMIN', 'MANAGER') NOT NULL
)
    charset = utf8mb4;

INSERT INTO testDB.user (firstname, lastname, email, password, role)
VALUES ('MEKCC', 'Petr', 'maks@gmail.com', '12345678', 'ADMIN');

CREATE TABLE IF NOT EXISTS book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    author VARCHAR(255),
    isbn VARCHAR(255),
    create_date DATETIME NOT NULL,
    last_modified DATETIME,
    created_by INT NOT NULL,
    last_modified_by INT
);

CREATE TABLE IF NOT EXISTS parent (
    id INT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS child (
    id INT AUTO_INCREMENT PRIMARY KEY,
    parent_id INT,
    FOREIGN KEY (parent_id) REFERENCES parent(id)
);

CREATE TABLE IF NOT EXISTS token (
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) UNIQUE NOT NULL,
    token_type ENUM('BEARER') NOT NULL,
    revoked TINYINT(1) NOT NULL,
    expired TINYINT(1) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user(id)
);
