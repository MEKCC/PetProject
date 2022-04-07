INSERT INTO testDB.user VALUES (1, 'jack@ozenero.com', 'Jack', 'jack123');
INSERT INTO testDB.user VALUES (2, 'adam@ozenero.com', 'Adam', 'adam123');

INSERT INTO testDB.role VALUES (1, 'ADMIN');
INSERT INTO testDB.role VALUES (2, 'USER');

INSERT INTO testDB.user_roles VALUES (1, 1);
INSERT INTO testDB.user_roles VALUES (1, 2);
INSERT INTO testDB.user_roles VALUES (2, 2);