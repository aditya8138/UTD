--2.1
select Dname, Count(*)
from department, Employee
where Dnumer=dno
group by Dname
having avg(Salary)>3000;

--2.2
select Dname, Count(*)
from Department, Employee
where Dnumber = Dno and Gender = 'M'
    and Dno in (select Dno from Employee group by Dno where avg(Salary)>3000)
group by Dname;

--2.3
select Fname,Lname
from Employee
where Dno in (select Dno from Employee
             where Salary = (select max(Salary) from Employee));

--2.4
select Fname,Lname
from Employee
where Salary >= 10000 + (select min(Salary) from Employee)

--2.5
select Fname,Lname
from Employee E1
where Salary = (select min(Salary)
                from Employee E2
                where E1.Dno = E2.Dno)
    and (select count(*) from Dependent D
         where E1.ssn = D.Essn) > 1;


--2.2.b

select Dname,Fname,Lname,
    (select count(*) from Employee E2 where E2.Dno = D.Dnumber) as Num_Emps,
    (select count(*) from Project P where P.Dno = D.Dnumber) as Num_Projs
from Department D, Employee M
where D.Mgr_ssn = E.ssn
