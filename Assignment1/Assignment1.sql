------------------------------------------------------------------------------
-----                              PART ONE                              -----
------------------------------------------------------------------------------

--Drop tables if already exist.
DROP TABLE BOOK_LOANS;
DROP TABLE BOOK_COPIES;
DROP TABLE BOOK_AUTHORS;
DROP TABLE BOOK;
DROP TABLE BORROWER;
DROP TABLE LIBRARY_BRANCH;
DROP TABLE PUBLISHER;

--Create all the tables given in the following schema (Figure 4.6).
--Define required constraints on given tables. Define triggered actions
--that will be attached to each foreign key constraint.
CREATE TABLE PUBLISHER
(
	"Name" VARCHAR2(50) NOT NULL,
	"Address" VARCHAR2(100),
	"Phone" VARCHAR2(10),
	PRIMARY KEY ("Name")
);

CREATE TABLE LIBRARY_BRANCH
(
	"Branch_id" CHAR(10) NOT NULL,
	"Branch_name" VARCHAR2(50),
	"Address" VARCHAR2(100),
	PRIMARY KEY ("Branch_id")
);

CREATE TABLE BORROWER
(
    "Card_no" CHAR(10) NOT NULL,
    "Name" VARCHAR2(50),
    "Address" VARCHAR2(100),
    "Phone" VARCHAR2(10),
    PRIMARY KEY ("Card_no")
);

CREATE TABLE BOOK
(
    "Book_id" VARCHAR2(13) NOT NULL,
    "Title" VARCHAR2(100),
    "Pubisher_name" VARCHAR2(50),
    PRIMARY KEY ("Book_id"),
    FOREIGN KEY ("Pubisher_name")
        REFERENCES "PUBLISHER" ("Name")
        ON DELETE CASCADE
);

CREATE TABLE BOOK_AUTHORS
(
    "Book_id" VARCHAR2(13) NOT NULL,
    "Author_name" VARCHAR2(50) NOT NULL,
    PRIMARY KEY ("Book_id", "Author_name"),
    FOREIGN KEY ("Book_id")
        REFERENCES "BOOK" ("Book_id")
        ON DELETE CASCADE
);

CREATE TABLE BOOK_COPIES
(
    "Book_id" VARCHAR2(13) NOT NULL,
    "Branch_id" CHAR(10) NOT NULL,
    "No_of_copies" NUMBER DEFAULT 0,
    PRIMARY KEY ("Book_id", "Branch_id"),
    FOREIGN KEY ("Book_id")
        REFERENCES "BOOK" ("Book_id")
        ON DELETE CASCADE,
    FOREIGN KEY ("Branch_id")
        REFERENCES "LIBRARY_BRANCH" ("Branch_id")
        ON DELETE CASCADE
);

CREATE TABLE BOOK_LOANS
(
    "Book_id" VARCHAR2(13) NOT NULL,
    "Branch_id" CHAR(10) NOT NULL,
    "Card_no" CHAR(10) NOT NULL,
    "Date_out" DATE,
    "Due_date" DATE,
    "Return_date" DATE DEFAULT NULL,
    PRIMARY KEY ("Book_id", "Branch_id", "Card_no"),
    FOREIGN KEY ("Book_id")
        REFERENCES "BOOK" ("Book_id")
        ON DELETE CASCADE,
    FOREIGN KEY ("Branch_id")
        REFERENCES "LIBRARY_BRANCH" ("Branch_id")
        ON DELETE CASCADE,
    FOREIGN KEY ("Card_no")
        REFERENCES "BORROWER" ("Card_no")
        ON DELETE CASCADE
);


--2. Insert two imaginary tuples into each table.
Insert into PUBLISHER values ('Pearson Education','330 Hudson New York City, New York','2029094520');
Insert into PUBLISHER values ('Addison-Wesley','75 Arlington Street, Suite 300, Boston, Mass','6178487500');
Insert into PUBLISHER values ('Prentice Hall','Upper Saddle River, New Jersey','8008489500');

Insert into LIBRARY_BRANCH values ('1111111111', 'Harrington','900 Civic Center Dr, Richardson, TX 75080');
Insert into LIBRARY_BRANCH values ('2222222222', 'Eugene McDermott Library','800 West Campbell Road, Richardson, TX 75080');
Insert into LIBRARY_BRANCH values ('3333333333', 'Renner Frankford Branch Library','6400 Frankford Rd, Dallas, TX 75252');

Insert into BORROWER values ('0000000001', 'Hanlin He', '955 W President George Bush Hwy, Richardson 75080', '4695620001');
Insert into BORROWER values ('0000000002', 'Xiaozhu Zhang', '800 W Renner Rd, Richardson, TX 75080', '4695620002');
Insert into BORROWER values ('0000000003', 'Michael Chen', '666 All Sain Lane, Plano, TX 75025', '4695620003');

Insert into BOOK values ('0136086209', 'Fundamentals of Database Systems (6th Edition)', 'Addison-Wesley');
Insert into BOOK values ('0134177304', 'Core Java Volume I--Fundamentals (10th Edition)', 'Prentice Hall');
Insert into BOOK values ('0132576279', 'Data Structures and Algorithm Analysis in Java (3rd Edition)', 'Pearson Education');

Insert into BOOK_AUTHORS values ('0136086209', 'Ramez Elmasri; Shamkant B. Navathe');
Insert into BOOK_AUTHORS values ('0134177304', 'Cay S. Horstmann');
Insert into BOOK_AUTHORS values ('0132576279', 'Mark A. Weiss');

Insert into BOOK_COPIES values ('0136086209', '1111111111', 55);
Insert into BOOK_COPIES values ('0136086209', '2222222222', 13);
Insert into BOOK_COPIES values ('0136086209', '3333333333', 20);
Insert into BOOK_COPIES values ('0134177304', '1111111111', 16);
Insert into BOOK_COPIES values ('0134177304', '2222222222', 12);
Insert into BOOK_COPIES values ('0134177304', '3333333333', 30);
Insert into BOOK_COPIES values ('0132576279', '1111111111', 18);
Insert into BOOK_COPIES values ('0132576279', '2222222222', 11);
Insert into BOOK_COPIES values ('0132576279', '3333333333', 40);

Insert into BOOK_LOANS values ('0136086209', '1111111111', '0000000001',
    to_date('12-SEP-16','DD-MON-RR'), to_date('30-SEP-16','DD-MON-RR'), NULL);
Insert into BOOK_LOANS values ('0134177304', '2222222222', '0000000001',
    to_date('02-JUL-16','DD-MON-RR'), to_date('24-OCT-16','DD-MON-RR'),
    to_date('10-SEP-16','DD-MON-RR'));
Insert into BOOK_LOANS values ('0132576279', '3333333333', '0000000002',
    to_date('06-AUG-16','DD-MON-RR'), to_date('30-DEC-16','DD-MON-RR'), NULL);


--3. Find the books that have been borrowed from Harrington Library in last 14 days and that have not been returned yet.
SELECT BOOK."Title"
FROM BOOK, BOOK_LOANS, LIBRARY_BRANCH
WHERE BOOK."Book_id" = BOOK_LOANS."Book_id"
    AND LIBRARY_BRANCH."Branch_id" = BOOK_LOANS."Branch_id"
    AND LIBRARY_BRANCH."Branch_name" = 'Harrington'
    AND BOOK_LOANS."Date_out" <= SYSDATE
    AND BOOK_LOANS."Date_out" >= SYSDATE - 14
    AND BOOK_LOANS."Return_date" IS NULL;

--For Harrington Library, increase number of copies for the book entitled “The Ocean” by 5.
UPDATE BOOK_COPIES
SET "No_of_copies" = "No_of_copies" + 5
WHERE BOOK_COPIES."Book_id" IN
        (SELECT "Book_id" FROM BOOK WHERE "Title" = 'The Ocean')
    AND BOOK_COPIES."Branch_id" IN
        (SELECT "Branch_id" FROM LIBRARY_BRANCH WHERE LIBRARY_BRANCH."Branch_name" = 'Harrington');

--5. Delete an existing borrower from the system. Explain how other tables are affected from this delete based on the triggered actions you have defined at #1.
DELETE FROM BORROWER
WHERE "Name" = 'Hanlin He';
--According to the triggered actions [ON DELETE CASCADE], once the tuple in the BORROWER table is deleted, all other tuples correlated to it were deleted in cascade.

------------------------------------------------------------------------------
-----                              PART TWO                              -----
------------------------------------------------------------------------------
--1. Write following queries in SQL.
----a. For each department whose average employee salary is more than $30,000, retrieve the department name and the number of employees working for that department.
SELECT DEPARTMENT.DNAME, COUNT(*) EMPLOYEE_NUMBER
FROM DEPARTMENT, EMPLOYEE
WHERE DEPARTMENT.DNO = EMPLOYEE.DNO
GROUP BY DEPARTMENT."DNAME"
HAVING AVG("SALARY") > 30000;

----b. Same as a, except output the number of male employees instead of the number of employees.
SELECT D.DNAME, COUNT(*) MALE_EMPLOYEE_NUMBER
FROM DEPARTMENT D, EMPLOYEE E
WHERE D.DNO = E.DNO
    AND E.SEX = 'M'
    AND D.DNO IN (SELECT DEPARTMENT.DNO
                                FROM DEPARTMENT, EMPLOYEE
                                WHERE DEPARTMENT.DNO = EMPLOYEE.DNO
                                GROUP BY DEPARTMENT.DNO
                                HAVING AVG("SALARY") > 30000)
GROUP BY D.DNAME;


----c. Retrieve the names of all employees who work in the department that has the employee with the highest salary among all employees.
SELECT EM.FNAME ||' '||EM.LNAME EMPLOYEE_NAME
FROM EMPLOYEE EM
WHERE EM.DNO IN (
    SELECT E.DNO
    FROM EMPLOYEE E
    WHERE E.SALARY = (
        SELECT MAX(SALARY)
        FROM EMPLOYEE)
    GROUP BY E.DNO);


----d. Retrieve the names of employees who make at least $10,000 more than the employee who is paid the least in the company.
SELECT E.FNAME ||' '||E.LNAME EMPLOYEE_NAME
FROM EMPLOYEE E
WHERE E.SALARY > 10000 + (SELECT MIN(SALARY) FROM EMPLOYEE);

----e. Retrieve the names of employees who is making least in their departments and have more than one dependent. (solve using correlated nested queries)

SELECT E.FNAME ||' '||E.LNAME EMPLOYEE_NAME
FROM EMPLOYEE E
WHERE (E.DNO, E.SALARY) IN ( --making least in their departments
        SELECT EM.DNO, MIN(SALARY)
        FROM EMPLOYEE EM
        GROUP BY EM.DNO)
    AND EXISTS ( --have more than one dependent
        SELECT *
        FROM DEPENDENT D
        WHERE E.SSN = D.ESSN
        GROUP BY D.ESSN
        HAVING COUNT(*) > 1);



--2. Specify following views in SQL. Solve questions using correlated nested queries (except a).
----a. A view that has the department name, manager name and manager salary for every department.
CREATE VIEW MGR_INFO_1
AS  SELECT D.DNAME DEPARTMENT_NAME, E.FNAME||' '||E.LNAME MGR_NAME, E.SALARY MGR_SALARY
    FROM DEPARTMENT D, EMPLOYEE E
    WHERE D.MGRSSN = E.SSN;


----b. A view that has the department name, its manager's name, number of employees working in that department, and the number of projects controlled by that department (for each department).
CREATE VIEW MGR_INFO_2
AS  SELECT D.DNAME, M.FNAME||' '||M.LNAME MGR_NAME, EMPLOYEES_NUM, PROJECT_NUM
    FROM (DEPARTMENT D JOIN EMPLOYEE M ON D.MGRSSN = M.SSN) FULL OUTER JOIN (
        SELECT DE.DNAME DEPARTMENT_NAME_E, COUNT(E.SSN) EMPLOYEES_NUM --get number of employees
        FROM DEPARTMENT DE, EMPLOYEE E
        WHERE DE.DNO = E.DNO
        GROUP BY DE.DNAME) ON D.DNAME = DEPARTMENT_NAME_E FULL OUTER JOIN (
            SELECT DP.DNAME DEPARTMENT_NAME_P, COUNT(P.PNO) PROJECT_NUM --get number of projects
            FROM DEPARTMENT DP, PROJECT P
            WHERE DP.DNO = P.DNO
            GROUP BY DP.DNAME) ON D.DNAME = DEPARTMENT_NAME_P;

----c. A view that has the project name, controlling department name, number of employees working on the project, and the total hours per week they work on the project (for each project).
CREATE VIEW PRJ_INFO_1
AS  SELECT P.PNAME, D.DNAME, EMPLOYEE_NUM, TOTAL_HOURS_PER_WEEK
    FROM (PROJECT P JOIN DEPARTMENT D ON P.DNO = D.DNO) JOIN (
        SELECT WORKS_ON.PNO E_PNO, COUNT(SSN) EMPLOYEE_NUM
        FROM WORKS_ON
        GROUP BY WORKS_ON.PNO) ON P.PNO = E_PNO JOIN (
            SELECT WORKS_ON.PNO H_PNO, SUM(HOURS) TOTAL_HOURS_PER_WEEK
            FROM WORKS_ON
            GROUP BY WORKS_ON.PNO) ON P.PNO = H_PNO;

----d. A view that has the project name, controlling department name, number of employees, and total hours worked per week on the project for each project with more than one employee working on it.
CREATE VIEW PRJ_INFO_2
AS  SELECT P.PNAME, D.DNAME, EMPLOYEE_NUM, TOTAL_HOURS_PER_WEEK
    FROM (PROJECT P JOIN DEPARTMENT D ON P.DNO = D.DNO) JOIN (
        SELECT WORKS_ON.PNO E_PNO, COUNT(SSN) EMPLOYEE_NUM
        FROM WORKS_ON
        GROUP BY WORKS_ON.PNO
        HAVING COUNT(SSN) > 1) ON P.PNO = E_PNO JOIN (
            SELECT WORKS_ON.PNO H_PNO, SUM(HOURS) TOTAL_HOURS_PER_WEEK
            FROM WORKS_ON
            GROUP BY WORKS_ON.PNO) ON P.PNO = H_PNO;


----e. A view that has the employee name, employee salary, department that the employee works in, department manager name, manager salary, and average salary for the department.
CREATE VIEW EMPLOYEE_INFO
AS  SELECT E.FNAME||' '||E.LNAME EMPLOYEE_NAME, E.SALARY EMPLOYEE_SALARY, DEPARTMENT_NAME, DEPT_MANAGER_NAME, MANAGER_SALARY, DEPARTMENT_AVG_SALARY
    FROM (EMPLOYEE E JOIN (
        SELECT DM.DNAME DEPARTMENT_NAME, DM.DNO DM_NO, EM.FNAME||' '||EM.LNAME DEPT_MANAGER_NAME, EM.SALARY MANAGER_SALARY
        FROM DEPARTMENT DM, EMPLOYEE EM
        WHERE DM.MGRSSN = EM.SSN) ON E.DNO = DM_NO) JOIN (
            SELECT ES.DNO ES_DNO, AVG(SALARY) DEPARTMENT_AVG_SALARY
            FROM EMPLOYEE ES
            GROUP BY ES.DNO) ON E.DNO = ES_DNO;


DROP VIEW MGR_INFO_1;
DROP VIEW MGR_INFO_2;
DROP VIEW PRJ_INFO_1;
DROP VIEW PRJ_INFO_2;
DROP VIEW EMPLOYEE_INFO;
