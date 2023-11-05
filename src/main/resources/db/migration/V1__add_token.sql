CREATE TABLE IF NOT EXISTS user (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    role ENUM('USER', 'ADMIN', 'MANAGER') NOT NULL
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
