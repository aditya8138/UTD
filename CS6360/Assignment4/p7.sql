CREATE OR REPLACE PROCEDURE
  Check_Loans (BName IN LIBRARY_BRANCH."Branch_name"%TYPE) AS
CURSOR BOOK_INFO IS
SELECT BOOK."Title",
       BORROWER."Name",
       BORROWER."Phone",
       BOOK_LOANS."Date_out",
       BOOK_LOANS."Return_date"
FROM BOOK_LOANS, BOOK, BORROWER, LIBRARY_BRANCH
WHERE BOOK_LOANS."Book_id" = BOOK."Book_id"
  AND BOOK_LOANS."Card_no" = BORROWER."Card_no"
  AND BOOK_LOANS."Branch_id" = LIBRARY_BRANCH."Branch_id";
thisRow BOOK_INFO%ROWTYPE;
BEGIN
    OPEN BOOK_INFO;
    DBMS_OUTPUT.PUT_LINE(
        'List of books borrowed from '
        || BName
        || 'within 30 days which have not been returned:'
    );
    LOOP
        FETCH BOOK_INFO INTO thisRow;
        EXIT WHEN (BOOK_INFO%NOTFOUND);
        IF(to_date(thisRow."Date_out",'DD-MON-YY') > to_date(SYSDATE,'DD-MON-YY') - 30
            AND thisRow."Return_date" IS NULL) THEN
          DBMS_OUTPUT.PUT_LINE('Book Title: ' || thisRow."Title" );
          DBMS_OUTPUT.PUT_LINE('Borrower Name: ' ||thisRow."Name");
          DBMS_OUTPUT.PUT_LINE('Borrower Phone: ' ||thisRow."Phone");
          DBMS_OUTPUT.PUT_LINE('');
        END IF;
    END LOOP;
    CLOSE BOOK_INFO;
END;
/

-- Validation of the previous procedure
DECLARE
  in_string LIBRARY_BRANCH."Branch_name"%TYPE := 'Harrington';

BEGIN
  Check_Loans(in_string);  -- Procedure invocation
END;
/
