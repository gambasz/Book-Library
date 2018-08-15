CREATE SEQUENCE course_sequence;
CREATE OR REPLACE TRIGGER coursect_tr
  BEFORE INSERT ON coursect
  FOR EACH ROW
BEGIN
  SELECT course_sequence.nextval
  INTO :new.id
  FROM dual;
END;
@
CREATE SEQUENCE person_sequence;
CREATE OR REPLACE TRIGGER person_tr
  BEFORE INSERT ON person
  FOR EACH ROW
BEGIN
  SELECT person_sequence.nextval
  INTO :new.id
  FROM dual;
END;
@
CREATE SEQUENCE resource_sequence;
CREATE OR REPLACE TRIGGER resource_tr
  BEFORE INSERT ON resources
  FOR EACH ROW
BEGIN
  SELECT resource_sequence.nextval
  INTO :new.id
  FROM dual;
END;
@
CREATE SEQUENCE publisher_sequence;
CREATE OR REPLACE TRIGGER publisher_tr
  BEFORE INSERT ON publishers
  FOR EACH ROW
BEGIN
  SELECT publisher_sequence.nextval
  INTO :new.id
  FROM dual;
END;
@
CREATE SEQUENCE semester_sequence;
CREATE OR REPLACE TRIGGER semester_tr
  BEFORE INSERT ON semester
  FOR EACH ROW
BEGIN
  SELECT semester_sequence.nextval
  INTO :new.id
  FROM dual;
END;
@

CREATE SEQUENCE rpres_sequence;
CREATE OR REPLACE TRIGGER PERSON_RESOURCES_TR
  BEFORE INSERT ON RELATION_PERSON_RESOURCES
  FOR EACH ROW
BEGIN
  SELECT rpres_sequence.nextval
  INTO :new.id
  FROM dual;
END;
@
CREATE SEQUENCE rcper_sequence;
CREATE OR REPLACE TRIGGER course_person_tr
  BEFORE INSERT ON RELATION_course_person
  FOR EACH ROW
BEGIN
  SELECT rcper_sequence.nextval
  INTO :new.id
  FROM dual;
END;
@
CREATE SEQUENCE rcres_sequence;
CREATE OR REPLACE TRIGGER course_resources_tr
  BEFORE INSERT ON RELATION_course_resources
  FOR EACH ROW
BEGIN
  SELECT rcres_sequence.nextval
  INTO :new.id
  FROM dual;
END;
@
CREATE SEQUENCE pubres_sequence;
CREATE OR REPLACE TRIGGER publisher_resource_tr
  BEFORE INSERT ON RELATION_publisher_resource
  FOR EACH ROW
BEGIN
  SELECT pubres_sequence.nextval
  INTO :new.id
  FROM dual;
END;
@
CREATE SEQUENCE rscou_sequence;
CREATE OR REPLACE TRIGGER semester_course_tr
  BEFORE INSERT ON RELATION_semester_course
  FOR EACH ROW
BEGIN
  SELECT rscou_sequence.nextval
  INTO :new.id
  FROM dual;
END;
