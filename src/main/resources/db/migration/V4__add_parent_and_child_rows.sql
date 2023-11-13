-- Записи для таблиці "parent"
INSERT INTO parent (id) VALUES (1);
INSERT INTO parent (id) VALUES (2);

-- Записи для таблиці "child" з посиланням на батьківську таблицю
INSERT INTO child (id, parent_id) VALUES (1, 1);
INSERT INTO child (id, parent_id) VALUES (2, 1);
INSERT INTO child (id, parent_id) VALUES (3, 2);
INSERT INTO child (id, parent_id) VALUES (4, 2);
INSERT INTO child (id, parent_id) VALUES (5, 2);

