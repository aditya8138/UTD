CREATE TABLE Customer_Log ( log_date DATE, action VARCHAR2(50),
  old_Phone VARCHAR2(10), old_Gender CHAR, old_Age NUMBER,
  old_Height FLOAT, old_Weight FLOAT, old_ClosetNo VARCHAR2(10),
  new_Phone VARCHAR2(10), new_Gender CHAR, new_Age NUMBER,
  new_Height FLOAT, new_Weight FLOAT, new_ClosetNo VARCHAR2(10)
);

CREATE OR REPLACE TRIGGER customerLogTrigger
  AFTER INSERT OR UPDATE OR DELETE ON CUSTOMER
      FOR EACH ROW
DECLARE
  log_action  Customer_Log.action%TYPE;
BEGIN
  IF INSERTING THEN
    log_action := 'Insert';
    INSERT INTO Customer_Log
    VALUES (SYSDATE, log_action, null, null, null, null, null, null,
            :new.PHONE, :new.GENDER, :new.AGE,
            :new.HEIGHT, :new.WEIGHT, :new.CLOSETNO );
  ELSIF UPDATING THEN
    log_action := 'Update';
    INSERT INTO Customer_Log
    VALUES (SYSDATE, log_action,
            :old.PHONE, :old.GENDER, :old.AGE,
            :old.HEIGHT, :old.WEIGHT, :old.CLOSETNO,
            :new.PHONE, :new.GENDER, :new.AGE,
            :new.HEIGHT, :new.WEIGHT, :new.CLOSETNO );
  ELSIF DELETING THEN
    log_action := 'Delete';
    INSERT INTO Customer_Log
    VALUES (SYSDATE, log_action,
            :old.PHONE, :old.GENDER, :old.AGE,
            :old.HEIGHT, :old.WEIGHT, :old.CLOSETNO,
            null, null, null, null, null, null );
  ELSE
    DBMS_OUTPUT.PUT_LINE('This code is not reachable.');
  END IF;
END;/
