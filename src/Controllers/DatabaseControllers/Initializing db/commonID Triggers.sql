create or replace TRIGGER cr_objectid_tr
  BEFORE INSERT ON RELATION_course_resources
  FOR EACH ROW
BEGIN
IF :new.commonid is null 
THEN 
    select id
  INTO :new.commonid
    from (select * from RELATION_SEMESTER_COURSE 
order by id desc) 
    where rownum = 1;
        END IF;
END;
@
create or replace TRIGGER cp_objectid_tr
  BEFORE INSERT ON RELATION_course_person
  FOR EACH ROW
BEGIN
IF :new.commonid is null 
THEN 
    select id
  INTO :new.commonid
    from (select * from RELATION_SEMESTER_COURSE 
order by id desc) 
    where rownum = 1;
        END IF;
END;
@
create or replace TRIGGER pr_objectid_tr
  BEFORE INSERT ON RELATION_PERSON_RESOURCES
  FOR EACH ROW
BEGIN
IF :new.commonid is null 
THEN 
    select id
  INTO :new.commonid
    from (select * from RELATION_SEMESTER_COURSE 
order by id desc) 
    where rownum = 1;
    END IF;
END;