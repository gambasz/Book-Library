CREATE TABLE coursect (
  id      NUMBER(38)    NOT NULL,
  title   VARCHAR2(32) NOT NULL,
  cnumber   VARCHAR2(32) NOT NULL,
  description   VARCHAR2(256) NOT NULL,
  department   VARCHAR2(128) NOT NULL
);
CREATE TABLE person (
  id      NUMBER(38)    NOT NULL,
  type   VARCHAR2(32) NOT NULL,
  firstname   VARCHAR2(32) NOT NULL,
  lastname   VARCHAR2(32) NOT NULL
);
CREATE TABLE publishers (
  id      NUMBER(38)    NOT NULL,
  title   VARCHAR2(128) NOT NULL,
  contact_info   VARCHAR2(256) NOT NULL,
  description   VARCHAR2(256) NOT NULL
);
CREATE TABLE resources (
  id      NUMBER(38)    NOT NULL,
  type   VARCHAR2(32) NOT NULL,
  title   VARCHAR2(256) NOT NULL,
  author   VARCHAR2(256) NOT NULL,
  isbn   VARCHAR2(64) NOT NULL,
  total_amount   NUMBER(38) NOT NULL,
  current_amount   NUMBER(38) NOT NULL,
  description   VARCHAR2(256) NOT NULL,
  ISBN13   VARCHAR2(32) NOT NULL,
  EDITION   VARCHAR2(32) NOT NULL
);
CREATE TABLE semester (
  id      NUMBER(38)    NOT NULL,
  season   VARCHAR2(32) NOT NULL,
  year   VARCHAR2(32) NOT NULL

);
CREATE TABLE relation_course_PERSON (
  courseid      NUMBER(38)    NOT NULL,
  personid      NUMBER(38)    NOT NULL,
  ID            NUMBER(38)    NOT NULL,
  COMMONID      NUMBER(38)    NOT NULL,
  COURSECRN     NUMBER(38),
  COURSENOTES   VARCHAR2(128)

);
CREATE TABLE relation_course_resources (
  courseid      NUMBER(38)    NOT NULL,
  resourceid    NUMBER(38)    NOT NULL,
  ID            NUMBER(38)    NOT NULL,
  COMMONID      NUMBER(38)    NOT NULL
);
CREATE TABLE relation_person_resources (
  courseid      NUMBER(38)    NOT NULL,
  resourceid    NUMBER(38)    NOT NULL,
  ID            NUMBER(38)    NOT NULL,
  COMMONID      NUMBER(38)    NOT NULL
);
CREATE TABLE relation_publisher_resource (
  publisherid      NUMBER(38)    NOT NULL,
  resourceid      NUMBER(38)    NOT NULL,
  ID            NUMBER(38)    NOT NULL
);
CREATE TABLE relation_semester_course (
  courseid      NUMBER(38)    NOT NULL,
  semesterid      NUMBER(38)    NOT NULL
);