CREATE TABLE IF NOT EXISTS book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    author VARCHAR(255),
    isbn VARCHAR(255),
    create_date DATETIME NOT NULL,
    last_modified DATETIME,
    created_by INT NOT NULL,
    last_modified_by INT
);
