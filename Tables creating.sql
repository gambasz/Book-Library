CREATE TABLE coursect (
  id      NUMBER(38)    NOT NULL,
  title   VARCHAR2(32) NOT NULL,
  cnumber   VARCHAR2(32) NOT NULL,
  description   VARCHAR2(64) NOT NULL,
  department   VARCHAR2(32) NOT NULL
);

CREATE TABLE person (
  id      NUMBER(38)    NOT NULL,
  type   VARCHAR2(32) NOT NULL,
  firstname   VARCHAR2(32) NOT NULL,
  lastname   VARCHAR2(32) NOT NULL
);