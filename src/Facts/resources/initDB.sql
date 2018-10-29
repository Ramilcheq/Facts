DROP TABLE IF EXISTS facts, rules, expressions, siblings;
DROP TYPE IF EXISTS expr_type;
CREATE TYPE expr_type AS ENUM ('or', 'and', 'fact');

CREATE TABLE facts (
  id   SERIAL PRIMARY KEY NOT NULL,
  fact VARCHAR(250)       NOT NULL
);

CREATE TABLE expressions (
  expression_id SERIAL PRIMARY KEY NOT NULL,
  type          expr_type          NOT NULL,
  fact          VARCHAR(250)
);

CREATE TABLE siblings (
  parent INT REFERENCES expressions (expression_id) NOT NULL,
  child  INT                             NOT NULL
);

CREATE TABLE rules (
  id            SERIAL PRIMARY KEY                         NOT NULL,
  expression_id INT REFERENCES expressions (expression_id) NOT NULL,
  result_fact   VARCHAR(250)                               NOT NULL
);

INSERT INTO facts (fact)
VALUES ('A'),
       ('_a'),
       ('a1'),
       ('a_1');

INSERT INTO expressions (type, fact)
VALUES ('or', ''),
       ('fact', 'A'),
       ('and', ''),
       ('and', ''),
       ('fact', 'B'),
       ('fact', 'C'),
       ('fact', 'D'),
       ('fact', 'E'),
       ('fact', 'F'),
       ('or', ''),
       ('fact', 'X'),
       ('fact', 'Y');

INSERT INTO siblings (parent, child)
VALUES (1, 2),
       (1, 3),
       (1, 4),
       (3, 5),
       (3, 6),
       (3, 7),
       (4, 8),
       (4, 9),
       (10, 11),
       (10, 12);

INSERT INTO rules (expression_id, result_fact)
VALUES (1, 'a'),
       (10, 'b');

